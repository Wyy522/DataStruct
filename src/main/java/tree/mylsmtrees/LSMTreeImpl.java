package tree.mylsmtrees;

import discipline.mylsmtree.SegmentImpl;
import sun.dc.pr.PRError;
import tree.mylsmtrees.Command;
import tree.mylsmtrees.WAL;
import tree.mylsmtrees.WALImpl;

import java.io.FileNotFoundException;
import java.io.IOException;

import static tree.mylsmtrees.Constant.TEST_PAGE_SIZE;

public class LSMTreeImpl {
    private String path;
    private Boolean isRunning;

    private SSTable ssTable;
    private MemTable memTable;
    private WAL wal;

    public LSMTreeImpl(String path) throws IOException {
        this.path = path;
        this.memTable = new MemTable();
        this.ssTable = new SSTable(path);
        this.wal = new WALImpl(path);
        this.isRunning = false;
    }

    public void start() {
        this.isRunning = true;
        //TODO reload data from wal and meta date from ssTable
        Thread t = new Thread(() -> {
            while (isRunning) {
                memTable=memTablePersist();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private MemTable memTablePersist() {
        if (memTable.getMemTableLength()>=TEST_PAGE_SIZE){
            memTable.setImmTable(true);
            Thread t = new Thread(()->{
                try {
                    doMemTablePersist(memTable);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            return new MemTable();
        }
        return memTable;
    }

    public void stop() {
        this.isRunning = false;

    }

    private void doMemTablePersist(MemTable memTable) throws IOException {
        ssTable.persistent(memTable,path);
    }

    public void set(String key, String value) throws IOException {
        Command command = new Command(1, key, value);
        wal.write(command);
        memTable.put(command);
    }
}
