package tree.mylsmtrees;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class LSMTreeImpl {
    private String path;
    private Boolean isRunning;
    private Boolean isPersist;
    EventBus eventBus = new EventBus();
    private SSTable ssTable;
    private MemTable memTable;
    private WAL wal;
    private List<MemTable> afterMergeMemTable;
    SSTableToMemIterator ssTableToMemIterator;

    public LSMTreeImpl(String path) throws IOException {
        this.path = path;
        this.memTable = new MemTable(eventBus);
        this.ssTable = new SSTable(path);
        this.wal = new WALImpl(path);
        this.isRunning = false;
        this.eventBus.register(this);
        this.ssTableToMemIterator = new SSTableToMemIterator();
        this.afterMergeMemTable=new ArrayList<>();
    }

    public void start() {
        this.isRunning = true;
        Thread thread = new Thread(() -> {
            while (isRunning) {
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
        Thread thread = new Thread(() -> {
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
            this.memTable = new MemTable(eventBus);
            memTable.put(command);
        }
    }

    public void merge() {
        SSTableToMem s0 = ssTableToMemIterator.ssTableToMemS.get(0);
        SSTableToMem s1 = ssTableToMemIterator.ssTableToMemS.get(1);
        s1.compare(s0,afterMergeMemTable);
        System.out.println("每个单页合并完结果为---------------------"+afterMergeMemTable);

    }


    public void loadSSTableToMemory(String path, int levelNumb, int numb) throws IOException {
        //存放所有meTable的数组(merge时用)
        ssTable.loadToMemory(path, levelNumb, numb, ssTableToMemIterator.ssTableToMemS);
    }

}
