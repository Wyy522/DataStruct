package tree.mylsmtree;

import java.io.IOException;
import java.util.TreeMap;

public interface Segment {

    void persist(TreeMap<String, Command> memTable) throws IOException;
}
