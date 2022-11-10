package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;
import discipline.mylsmtree.SparseIndex;

import java.util.ArrayList;
import java.util.List;

import static tree.mylsmtrees.Constant.PAGE_SIZE;

public class ParseIndex {
    private List<SparseIndexItem> indexItems;
    public ParseIndex() {
        indexItems=new ArrayList<>();
    }

    public void addIndex(String key,String value , int len, int pageNumb){
        SparseIndexItem item = new SparseIndexItem(key, value, len, pageNumb);
        indexItems.add(item);
    }

    public byte[] toByteArray() {
        return JSON.toJSONBytes(indexItems);
    }

    public List<SparseIndexItem> getIndexItems() {
        return indexItems;
    }

    public void setIndexItems(List<SparseIndexItem> indexItems) {
        this.indexItems = indexItems;
    }
    private static class SparseIndexItem {
        String key;
        String value;
        int pageNumb;
        long offset;
        int len;
        //TODO bolon filter

        public SparseIndexItem(String key,String value, int len, int pageNumb) {
            this.key = key;
            this.value=value;
            this.pageNumb = pageNumb;
            this.offset = (long) pageNumb *PAGE_SIZE;
            this.len = len;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getPageNumb() {
            return pageNumb;
        }

        public void setPageNumb(int pageNumb) {
            this.pageNumb = pageNumb;
        }

        public long getOffset() {
            return offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }
    }
}