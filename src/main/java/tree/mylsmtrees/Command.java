package tree.mylsmtrees;

import com.alibaba.fastjson.JSON;

public class Command {
    public static final int OP_SET = 1;
    public static final int OP_GEt = 2;
    public static final int OP_RM = 3;

    private int op;
    private String key;
    private String value;

    public Command(int op, String key, String value){
        this.op=op;
        this.key=key;
        this.value=value;
    }
    public int getBytes(Command command){
        return JSON.toJSONBytes(command).length;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Command{" +
                "op=" + op +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
