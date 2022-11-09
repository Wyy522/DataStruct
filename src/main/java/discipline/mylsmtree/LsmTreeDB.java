package discipline.mylsmtree;

import discipline.mylsmtree.excpetion.ioException;
import discipline.mylsmtree.excpetion.writeException;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/*
 * 写流程
 * 加锁
 * 包装成command对象
 * 写入WAL
 * 写Mem
 * */
public class LsmTreeDB {

    public static final int PART_SIZE = 1024;
    public static final int MEM_TABLE_MAX_SIZE = 10;

    private String path;
    private Boolean isRunning;

    private TreeMap<String, Command> memTable;
    private TreeMap<String, Command> immutableMemTable;
    private int levelNumb;
    private int numb;

    private SSTableImpl ssTable;
    private TreeMap<Integer, Deque<SegmentImpl>> ssTables;
    private Deque<SegmentImpl> level;

    private WAL wal;

    public LsmTreeDB(String path) throws IOException {
        this.path = path;
        memTable = new TreeMap<>();
        immutableMemTable = new TreeMap<>();
        ssTable = new SSTableImpl(PART_SIZE, path);
        ssTables = new TreeMap<Integer, Deque<SegmentImpl>>();
        level = new LinkedList<SegmentImpl>();
        wal = new WALImpl(path);
    }

    public void start() {
        this.isRunning = true;
        //TODO reload data from wal and meta date from ssTable
        Thread t = new Thread(() -> {
            while (isRunning) {
                memTablePersist();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void stop() {
        this.isRunning = false;
        int i = 0;
        for (SegmentImpl s :
                level) {
            System.out.println(s.toString());
        }
//        System.out.println("key:" + e.getKey() + ",value:" + (((LinkedList) e.getValue()).get(i++)).toString());
//        System.out.println(ssTables.size());
    }

    public void set(String key, String value) throws InterruptedException {
        Command command = new Command(1, key, value);
        memTable.put(key, command);
        try {
            wal.write(command);
        } catch (IOException e) {
            throw new writeException(e.toString());
        }
    }

    public void memTablePersist() {
        if (memTable.size() >= MEM_TABLE_MAX_SIZE) {
            doMemTablePersist();
        }
    }

    public void doMemTablePersist() {
        //将memTable转换为immutableMemTable
        for (Map.Entry<String, Command> entry : memTable.entrySet()) {
            String key = entry.getKey();
            Command command = entry.getValue();
            immutableMemTable.put(key, command);
        }
        //immutableMemTable落盘
        memTable.clear();

        SegmentImpl segment = new SegmentImpl(path, PART_SIZE, levelNumb, numb);
        ssTable.persistent(immutableMemTable, segment, levelNumb, numb++, level);
        level.offerFirst(segment);
        immutableMemTable.clear();
        try {
            wal.clear();
        } catch (IOException e) {
            throw new ioException(e.toString());
        }
        if (numb % 2 == 0) {
            levelNumb++;
            numb = 0;
        }
    }
}
