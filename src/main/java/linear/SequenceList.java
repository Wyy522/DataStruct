package linear;

import java.util.Arrays;
import java.util.Iterator;

public class SequenceList<T> {

    // 存储元素的数组
    private T[] arr;

    // 记录当前顺序表中的元素个数
    private int N;

    // 构造方法
    public SequenceList(int capacity) {
        this.arr = (T[]) new Object[capacity];
        this.N = 0;
    }

    // 清空
    public void clear() {
        this.N = 0;
    }

    // 判断是否为空
    public boolean isEmpty() {
        return this.N == 0;
    }

    // 获取长度
    public int length() {
        return this.N;
    }

    // 指定位置的元素
    public T get(int i) {
        if (i > N) {
            return null;
        }
        return arr[i];
    }

    // 指定位置添加元素
    public void insert(int i, T t) {
        if (i >= arr.length) {
            resize(i * 2);
        }
        for (int j = i; j < arr.length - 1; j++) {
            arr[j + 1] = arr[j];
        }
        arr[i] = t;

    }

    // 尾部添加元素
    public void insert(T t) {
        insert(N++, t);
    }

    private void resize(int newSize) {

        T[] newArr = (T[]) new Object[newSize];
        int i = 0;

        for (T t : arr.length<=newSize?arr:newArr) {
            newArr[i] = arr[i];
            i++;
        }
        arr = newArr;
    }

    @Override
    public String toString() {
        return "SequenceList{" +
                "arr=" + Arrays.toString(arr) +
                ", N=" + N +
                '}';
    }

    // 删除指定位置元素，并返回该元素
    public T remove(int i) {
        if (i > N) {
            return null;
        }
        T old = arr[i];
        for (int j = i; j < N - 1; j++) {
            arr[j] = arr[j + 1];
        }

        arr[--N]=null;

        if (N<=arr.length/2){
            resize(arr.length/2);
        }

        return old;

    }

    public Iter It() {
        return new Iter();
    }


    private class Iter implements Iterator<T> {
        int p = 0;

        @Override
        public boolean hasNext() {
            return p == N;
        }

        @Override
        public T next() {
            return arr[p++];
        }
    }
}
