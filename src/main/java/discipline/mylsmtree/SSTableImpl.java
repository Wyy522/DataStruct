package discipline.mylsmtree;

import discipline.mylsmtree.excpetion.ioException;

import java.io.IOException;
import java.util.Deque;
import java.util.TreeMap;

public class SSTableImpl {
    private int partSize;
    private String path;

    public SSTableImpl( int partSize, String path ) {
        this.partSize = partSize;
        this.path = path;
    }

    public void persistent(TreeMap<String, Command> memTable, SegmentImpl segment, int levelNumb, int numb, Deque<SegmentImpl> level){

        try {
            segment.persist(memTable);
        } catch (IOException e) {
            throw new ioException(e.toString());
        }


    }

    public void merge(){

    }
}
