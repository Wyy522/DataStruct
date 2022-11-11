package tree.mylsmtrees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSTableToMemIterator implements Iterator<SSTableToMem> {
    List<SSTableToMem> ssTableToMemS;

    public SSTableToMemIterator() {
        this.ssTableToMemS = new ArrayList<>();
    }

    @Override
    public boolean hasNext() {
        return ssTableToMemS.iterator().hasNext();
    }

    @Override
    public SSTableToMem next() {
        return ssTableToMemS.iterator().next();
    }

    @Override
    public String toString() {
        return "SSTableToMemIterator{" +
                "ssTableToMemS=" + ssTableToMemS +
                '}';
    }
}
