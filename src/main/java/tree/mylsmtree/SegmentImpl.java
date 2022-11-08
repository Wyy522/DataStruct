package tree.mylsmtree;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.TreeMap;

public class SegmentImpl implements Segment {
    private final String SEGMENT_FILE_NAME = "sst_";
    private final int SEGMENT_SIZE = 1024;
    private String path;
    private int levelNumb;
    private int numb;
    private int partSize;
    private SparseIndex sparseIndex;
    private RandomAccessFile reader;

    public SegmentImpl(String path, int partSize, int levelNumb,int numb) {
        this.path = path;
        this.levelNumb = levelNumb;
        this.partSize = partSize;
        this.numb=numb;
    }

    @Override
    public void persist(TreeMap<String, Command> memTable) throws IOException {
        String fileName = FileUtils.buildFileName(path, String.valueOf(levelNumb),String.valueOf(numb), SEGMENT_FILE_NAME);
        RandomAccessFile writer = new RandomAccessFile(fileName, "rw");
        this.reader = new RandomAccessFile(fileName, "r");
        writer.seek(SegmentMetaData.META_DATA_SIZE);
        //第一个key的index位置
        long offset = SegmentMetaData.META_DATA_SIZE;
        int size = 0;
        int dataSize = 0;
        String sparseIndexKey = "";
        this.sparseIndex = new SparseIndex();
        for (Command command : memTable.values()) {
            if (sparseIndexKey.equals("")) {
                sparseIndexKey = command.getKey();
            }
            byte[] json = JSON.toJSONBytes(command);
            //写入数据
            writer.writeInt(json.length);
            writer.write(json);
            //command整体长度
            int len = 4 + json.length;
            //这一段command整体的长度(映射到索引文件的条件)
            size=size+len;
            //所有command的长度
            dataSize=dataSize+len;
            if (size >= partSize) {
                //写入稀疏索引(数组实现)
                sparseIndex.addIndex(sparseIndexKey, offset, size);
                //后面的key的index位置
                offset += size;
                size = 0;
                sparseIndexKey = "";
            }
        }
        if (size > 0) {
            //最后一个key也写入稀疏索引(数组实现)
            sparseIndex.addIndex(sparseIndexKey, offset, size);
        }
        //稀疏索引持久化
        byte[] indexData = sparseIndex.toByteArray();
        writer.write(indexData);
        //写入元信息(元信息大小,总数据长度,稀疏索引的offset,稀疏索引的长度)
        SegmentMetaData metaData = new SegmentMetaData(SegmentMetaData.META_DATA_SIZE, dataSize,
                SegmentMetaData.META_DATA_SIZE + dataSize, indexData.length);
        writer.seek(0);
        writer.write(metaData.toByteArray());
        writer.close();

    }

    @Override
    public String toString() {
        return "SegmentImpl{" +
                "SEGMENT_FILE_NAME='" + SEGMENT_FILE_NAME + '\'' +
                ", SEGMENT_SIZE=" + SEGMENT_SIZE +
                ", path='" + path + '\'' +
                ", levelNumb=" + levelNumb +
                ", numb=" + numb +
                ", partSize=" + partSize +
                ", sparseIndex=" + sparseIndex +
                ", reader=" + reader +
                '}';
    }
}
