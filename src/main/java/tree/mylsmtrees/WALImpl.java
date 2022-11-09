package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;
import tree.mylsmtrees.Command;
import tree.mylsmtrees.excpetion.ioException;
import tree.mylsmtrees.excpetion.randomAccessFileException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;

public class WALImpl implements WAL{
    private String path;
    private final String WAL_File_NAME="wal";

    private final int WAL_OP=-1;
    private RandomAccessFile writer;
    private RandomAccessFile reader;

    public WALImpl(String path) throws IOException {
        this.path = path;
        this.writer=new RandomAccessFile(FileUtils.buildFileName(path,String.valueOf(WAL_OP),String.valueOf(WAL_OP),WAL_File_NAME),"rw");
        this.reader=new RandomAccessFile(FileUtils.buildFileName(path,String.valueOf(WAL_OP),String.valueOf(WAL_OP),WAL_File_NAME),"r");
        writer.seek(writer.length());
    }

    @Override
    public void write(Command command) throws IOException {
        byte[] commandBytes = JSON.toJSONBytes(command);
        writer.writeInt(commandBytes.length);
        writer.write(commandBytes);
    }

    @Override
    public Optional<Command> read() throws IOException {
        return Optional.empty();
    }

    @Override
    public void clear() throws IOException {

    }
}
