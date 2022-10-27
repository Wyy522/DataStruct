package test;

public class TestFactorial {
    public static void main(String[] args) {
        System.out.println(Factorial(10));
        //System.out.println(Factorial(100000)); 一直压栈导致StackOverflowError
    }

    public static long Factorial(int n){
        if (n==1){
            return  1;
        }

        return n*Factorial(n-1);
    }
}
