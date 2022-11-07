package tree.mylsmtree;

import org.junit.Test;

public class LsmTreeDBTest {

    @Test
    public void LsmTreeDBCreatingTest(){
        LsmTreeDB treeDB=new LsmTreeDB("");
        treeDB.start();
    }
}
