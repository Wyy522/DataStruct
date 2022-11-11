package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static tree.mylsmtrees.Constant.*;

public class MemTable {
    TreeMap<String, Command> memTable;

    private int levelNumb;
    private int memTableLength;
    EventBus eventBus;
    private volatile boolean isImmTable = false;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MemTable() {
        this(new EventBus());
    }

    public MemTable(EventBus eventBus) {
        this.memTable = new TreeMap<String, Command>();
        this.eventBus=eventBus;
    }

    public boolean put(Command command) {
        try {
            lock.writeLock().lock();
            memTableLength+=command.getBytes(command);
            if (memTableLength<=PAGE_SIZE){
                memTable.put(command.getKey(),command);
                System.out.println(command.toString());
                return true;
            }else{
                memTableLength=0;
                System.out.println(memTableLength);
                eventBus.post(memTable);
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clear() {
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

    @Override
    public String toString() {
        return "MemTable{" +
                "memTable=" + memTable +
                ", levelNumb=" + levelNumb +
                ", memTableLength=" + memTableLength +
                ", isImmTable=" + isImmTable +
                '}'+'\n';
    }
}
