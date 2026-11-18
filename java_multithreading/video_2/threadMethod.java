package video_2;

public class threadMethod {
    public static void main(String[] args) {
        // currentThread static method of thread class used to get object reference of thread which is been executed
        // getName method used to get name of thread
        Thread t1 = new Thread(()->System.out.println("Current thread is "+ Thread.currentThread().getName()));

        t1.start(); // -> currentThread = Thread-0 when run is called through start method
        // t1.start(); // -> Can we run same thrad twice ? => no
        t1.run(); // -> currentThread = main if run is directely called
    }
}
