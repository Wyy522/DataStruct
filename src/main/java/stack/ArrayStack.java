package stack;

public class ArrayStack {

    private int maxStack;

    private int[] stack;

    private int top = -1;

    public ArrayStack(int maxStack) {
        this.maxStack = maxStack;
        stack=new int[maxStack];
    }


    /*
    需求：
    1.压栈
    2.弹栈
    3.判断是否是空栈
    4.判断是否是满栈
    * */

    private boolean isFull() {
        return this.top == this.maxStack - 1;
    }

    private boolean isEmpty() {
        return this.top == -1;
    }

    public void push(int val) {
        if (isFull()) {
            throw new RuntimeException("此栈已满");
        }
        top++;
        stack[top] = val;
    }

    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("此栈已空");
        }
        int res=stack[top];
        top--;
        return res;
    }

    public void list(){
        if (isEmpty()){
            throw new RuntimeException("此栈已空");
        }
        for (int i = 0; i < stack.length; i++) {
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }

}
