package stack;

public class TestApp {
    /*
    * 回文数据
    * */
    public static void main(String[] args) {
        System.out.println(palindrome("aba"));
    }

    public static boolean palindrome(String val){
        ArrayStack arrayStack = new ArrayStack(10);
        int length = val.length();

        for (int i = 0; i < length; i++) {
            arrayStack.push(val.charAt(i));
        }

        String newvVal="";
        for (int i = 0; i < length; i++) {
            char pop = (char) arrayStack.pop();
            newvVal=newvVal+pop;
        }

        if (val.equals(newvVal)){
            return true;
        }
        return false;
    }
}
