package tree.mylsmtrees;

import java.nio.ByteBuffer;

import static tree.mylsmtrees.Constant.META_DATA_SIZE;

public class SSTableMetaData {
    private int numb;
    private int level;
    private long dataOffset;
    private int dataLen;

    public SSTableMetaData(int numb, int level, long dataOffset, int dataLen) {
        this.numb = numb;
        this.level = level;
        this.dataOffset = dataOffset;
        this.dataLen = dataLen;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(META_DATA_SIZE);
        buffer.putInt(numb);
        buffer.putInt(level);
        buffer.putLong(dataOffset);
        buffer.putInt(dataLen);
        return buffer.array();
    }

    @Override
    public String toString() {
        return "SSTableMetaData{" +
                "numb=" + numb +
                ", level=" + level +
                ", dataOffset=" + dataOffset +
                ", dataLen=" + dataLen +
                '}';
    }
}
