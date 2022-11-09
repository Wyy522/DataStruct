package tree.mylsmtrees;

import java.io.IOException;
import java.util.Optional;

public interface WAL {
    //写入写前日志(一般在写入时先写WAL)
    void write(Command command) throws IOException;

    //读出写前日志
    Optional<Command> read() throws IOException;

    //清空写前日志(当已经落盘后清空WAL)
    void clear() throws IOException;
}
