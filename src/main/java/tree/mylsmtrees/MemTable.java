package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;

import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemTable {
    TreeMap<String, Command> memTable;
    private int levelNumb;
    private int memTableLength;
    private volatile boolean  isImmTable = false;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MemTable() {
        this.memTable = new TreeMap<String, Command>();
    }

    public void put(Command command) {
        try {
            lock.writeLock().lock();
            memTable.put(command.getKey(), command);
            memTableLength+=JSON.toJSONBytes(command).length;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clear(){
        memTable.clear();
    }

    public int getMemTableLength() {
        return memTableLength;
    }

    public TreeMap<String, Command> getMemTable() {
        return memTable;
    }

    public void setMemTable(TreeMap<String, Command> memTable) {
        this.memTable = memTable;
    }

    public int getLevelNumb() {
        return levelNumb;
    }

    public void setLevelNumb(int levelNumb) {
        this.levelNumb = levelNumb;
    }

    public void setMemTableLength(int memTableLength) {
        this.memTableLength = memTableLength;
    }

    public boolean getIsImmTable() {
        return isImmTable;
    }

    public void setImmTable(boolean immTable) {
        isImmTable = immTable;
    }

}
