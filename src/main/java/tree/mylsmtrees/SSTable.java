package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;
import discipline.mylsmtree.SegmentMetaData;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import static tree.mylsmtrees.Constant.*;

public class SSTable {
    private static int levelNumb;
    private static final String SSTable_FILE_NAME = "sst";
    private int numb;
    private String path;
    private RandomAccessFile reader;
    private ParseIndex parseIndex;

    public SSTable(String path) throws FileNotFoundException {
        this.path = path;
        reader = new RandomAccessFile(FileUtils.buildFileName(path, String.valueOf(levelNumb), String.valueOf(numb), SSTable_FILE_NAME), "rw");
    }

    public void persistent(TreeMap<String, Command> memTable, String path) throws IOException {
        String filename = FileUtils.buildFileName(path, String.valueOf(levelNumb), String.valueOf(numb), SSTable_FILE_NAME);
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        parseIndex = new ParseIndex();
        int pageNumb = 1;
        int dataPageSize=0;
        int dataSize = 0;
        writer.seek((long) pageNumb * TEST_THRESHOLD_SIZE);
        for (Command c : memTable.values()) {
            byte[] json = JSON.toJSONBytes(c);
            //command整体长度
            int len = 4 + json.length;
            //所有command的长度
            dataSize = dataSize + len;
            //单页长度
            dataPageSize=dataPageSize+len;
            if (dataPageSize>=TEST_PAGE_MAX){
                System.out.println(dataPageSize);
                //最后一条Key
                parseIndex.addIndex(c.getKey(), c.getValue(), dataPageSize, pageNumb);
                dataPageSize=0;
                writer.seek((long) ++pageNumb * TEST_THRESHOLD_SIZE);
            }
            writer.writeInt(json.length);
            writer.write(json);
        }
        if (dataPageSize>=0){
            parseIndex.addIndex(memTable.lastEntry().getKey(), memTable.lastEntry().getValue().getValue(), dataPageSize, pageNumb);
        }
        writer.seek(0);
        //写入元数据
        SSTableMetaData ssTableMetaData = new SSTableMetaData(numb++, levelNumb, PAGE_SIZE, pageNumb*PAGE_SIZE);
        writer.write(ssTableMetaData.toByteArray());
        //写入稀疏索引
        writer.write(parseIndex.toByteArray());
        writer.close();
    }

    public void loadToMemory(String path,int levelNumb,int numb) throws IOException {
        MemTable memTable = new MemTable();
        reader=new RandomAccessFile(FileUtils.buildFileName(path, String.valueOf(levelNumb), String.valueOf(numb), SSTable_FILE_NAME), "r");
        reader.seek(1024);
        while(true){
            int i = reader.readInt();
            ByteBuffer bytes=ByteBuffer.allocate(i);
            reader.read(bytes.array());
            Command cmd = JSON.parseObject(bytes.array(), 0, i, StandardCharsets.UTF_8, Command.class);
            if(cmd==null){
                break;
            }
            memTable.put(cmd);
        }
        System.out.println("-----------------------------");
        reader.seek(2048);
        while(true){
            int i = reader.readInt();
            ByteBuffer bytes=ByteBuffer.allocate(i);
            reader.read(bytes.array());
            Command cmd = JSON.parseObject(bytes.array(), 0, i, StandardCharsets.UTF_8, Command.class);
            if(cmd==null){
                break;
            }
            memTable.put(cmd);
        }
    }
}
