package video_2;

public class threadExecutionOrder {
    // Thread has undetermistic execution order, so we cannot predict which thread will execute first
    public static void main(String[] args) {
        Thread t1 = new Thread(()->System.out.println("Thread 1"));
        Thread t2 = new Thread(()->System.out.println("Thread 2"));
        Thread t3 = new Thread(()->System.out.println("Thread 3"));

        t1.start();
        t2.start();
        t3.start();
    }
}
