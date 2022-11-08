package tree.mylsmtree;

import tree.mylsmtree.excpetion.ioException;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeMap;

public class SSTableImpl {
    private int partSize;
    private String path;

    public SSTableImpl( int partSize, String path ) {
        this.partSize = partSize;
        this.path = path;
    }

    public void persistent(TreeMap<String, Command> memTable,int levelNumb,int numb,Deque<SegmentImpl> level){

        SegmentImpl segment=new SegmentImpl(path, partSize,levelNumb,numb);
        try {
            segment.persist(memTable);
        } catch (IOException e) {
            throw new ioException(e.toString());
        }
        level.offerFirst(segment);


    }

    public void merge(){

    }
}
