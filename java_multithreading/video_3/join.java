package video_3;

public class join {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("Thread 1 is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread 1 is terminating");
        });

        Thread t2 = new Thread(()->{
            System.out.println("Thread 2 is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread 2 is terminating");
        });

        t1.start();
        t2.start();

        // join method will make main thread wait for t1 and t2 to complete execution
        t1.join();
        t2.join(2000); // main thread will wait for t2 to complete execution for 2 seconds if t2 is not completed in 2 seconds then main thread will continue execution

        System.out.println("Main thread is terminating");
    }
}
