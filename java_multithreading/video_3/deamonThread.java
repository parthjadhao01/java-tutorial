package video_3;

public class deamonThread {
    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            while(true){
                System.out.println("Daemon thread is running");
            }
        });
        t1.setDaemon(true);
        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread is terminating");
    }
}

/* A daemon thread is a thread that runs in the background to perform tasks such as garbage collection, finalization,
and other system-level operations. The JVM does not wait for daemon threads to complete before exiting.
By default, all threads created by a program are non-daemon threads. To create a daemon thread,
we can use the setDaemon(true) method of the Thread class. */
