package tree.mylsmtree;

import tree.mylsmtree.excpetion.ioException;
import tree.mylsmtree.excpetion.writeException;

import java.io.IOException;
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
    private SSTableImpl ssTable;
    private WAL wal;

    public LsmTreeDB(String path) {
        this.path = path;
        memTable = new TreeMap<>();
        immutableMemTable = new TreeMap<>();
        ssTable = new SSTableImpl(0,PART_SIZE,path);
        wal = new WALImpl(path);
    }

    public void start() {
        this.isRunning=true;
        //TODO reload data from wal and meta date from ssTable
        Thread t=new Thread(()->{
            while(isRunning){
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

    public void stop(){
        this.isRunning=false;
    }

    public void set(String key ,String value) throws InterruptedException {
        Command command=new Command(1,key,value);
        memTable.put(key,command);
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
        ssTable.persistent(immutableMemTable);
        immutableMemTable.clear();
        try {
            wal.clear();
        } catch (IOException e) {
            throw new ioException(e.toString());
        }
    }
}
