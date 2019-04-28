package test.java.thread;

public class TestThread {
    public static void main(String[] args) {
     //thread1();
     thread2();
    }

    private static void thread1() {
        MyThread a = new MyThread("a");
        MyThread b = new MyThread("b");
        MyThread c = new MyThread("c");
        a.start();
        b.start();
        c.start();

    }

    private static void thread2() {
        MyThread  myThread = new MyThread(); //三个线程共享一个变量5
        Thread a = new Thread(myThread,"a");
        Thread b = new Thread(myThread,"b");
        Thread c = new Thread(myThread,"c");
        a.start();
        b.start();
        c.start();

    }

}
