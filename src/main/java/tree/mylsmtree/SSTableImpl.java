package tree.mylsmtree;

import tree.mylsmtree.excpetion.ioException;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeMap;

public class SSTableImpl {
    private int levelNumb;
    private int segmentId;
    private int partSize;
    private String path;
    private Deque<Segment> segments;

    public SSTableImpl(int segmentId, int partSize, String path) {
        this.segmentId = segmentId;
        this.partSize = partSize;
        this.path = path;
        this.segments=new LinkedList<>();
    }

    public void persistent(TreeMap<String, Command> memTable){
        segmentId++;
        SegmentImpl segment=new SegmentImpl(path, segmentId, levelNumb, partSize);
        try {
            segment.persist(memTable);
        } catch (IOException e) {
            throw new ioException(e.toString());
        }
        segments.offerFirst(segment);
    }
}
