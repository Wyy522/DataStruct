package tree.mylsmtrees;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import discipline.mylsmtree.SegmentImpl;
import sun.dc.pr.PRError;
import tree.mylsmtrees.Command;
import tree.mylsmtrees.WAL;
import tree.mylsmtrees.WALImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;


public class LSMTreeImpl {
    private String path;
    private Boolean isRunning;
    private Boolean isPersist;
    EventBus eventBus=new EventBus();
    private SSTable ssTable;
    private MemTable memTable;
    private WAL wal;

    public LSMTreeImpl(String path) throws IOException {
        this.path = path;
        this.memTable = new MemTable(eventBus);
        this.ssTable = new SSTable(path);
        this.wal = new WALImpl(path);
        this.isRunning = false;
        this.eventBus.register(this);
    }

    public void start() {
        this.isRunning = true;
        Thread thread=new Thread(()->{
            while (isRunning){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    public void stop() throws IOException {
        this.isRunning = false;
    }

    @Subscribe
    private void doMemTablePersist(TreeMap<String, Command> memTable) throws IOException {
        System.out.println("log :正在持久化");
        Thread thread=new Thread(()->{
            try {
                ssTable.persistent(memTable, path);
                wal.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void set(String key, String value) throws IOException, InterruptedException {
        Command command = new Command(1, key, value);
        wal.write(command);
        if (!memTable.put(command)) {
            Thread.sleep(1000);
            this.memTable=new MemTable(eventBus);
            memTable.put(command);
        }
    }

    public void merge(){

    }

    public void loadSSTableToMemory(String path,int levelNumb,int numb) throws IOException {
        ssTable.loadToMemory(path,levelNumb,numb);
    }

}
