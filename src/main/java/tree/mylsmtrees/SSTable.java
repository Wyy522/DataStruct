package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;
import discipline.mylsmtree.SegmentMetaData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
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

    public void persistent(MemTable memTable, String path) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(TEST_PAGE_SIZE);
        String filename = FileUtils.buildFileName(path, String.valueOf(levelNumb), String.valueOf(numb), SSTable_FILE_NAME);
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        parseIndex = new ParseIndex();
//        byte[] commandJsonBytes=new byte[PAGE_SIZE];
        int cOldLength = 0;
        int cNewLength = 0;
        int pageNumb = 1;
        TreeMap<String, Command> command = memTable.getMemTable();
        long offset = SegmentMetaData.META_DATA_SIZE;
        int dataSize = 0;
        for (Command c : command.values()) {
            //分页
            cOldLength += cNewLength;
            cNewLength = c.getBytes(c);
            if (cNewLength + cOldLength <= 256) {
                byte[] json = JSON.toJSONBytes(command);
                byteBuffer.putInt(json.length);
                byteBuffer.put(json);
                //command整体长度
                int len = 4 + json.length;
                //所有command的长度
                dataSize = dataSize + len;
            } else {
                writer.seek(pageNumb * META_DATA_SIZE);
                writer.write(byteBuffer.array());
                parseIndex.addIndex(c.getKey(), c.getValue(), cOldLength, pageNumb);
                pageNumb++;
                cOldLength = 0;
                byteBuffer.clear();
            }
            //最后一条Key
            if (cOldLength > 0) {
                parseIndex.addIndex(c.getKey(), c.getValue(), cOldLength, pageNumb);
            }
            writer.seek(0);
            //写入元数据
            SSTableMetaData ssTableMetaData=new SSTableMetaData(numb,levelNumb,PAGE_SIZE,dataSize);
            writer.write(ssTableMetaData.toByteArray());
            //写入稀疏索引
            byte[] indexData = parseIndex.toByteArray();
            writer.write(indexData);
            writer.close();

        }

        //writer.write(byteBuffer.array());
    }


}
