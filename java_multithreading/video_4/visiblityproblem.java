package video_4;

// Visibility problem occurs when one thread is not able to see the changes made by another thread.
// In this example, thread t2 is waiting for the flag to be set to true by thread t1,
// but it may not see the change due to caching or compiler optimizations.
// This can lead to an infinite loop in thread t2.

public class visiblityproblem {
    
    // volatile keyword is used to indicate that a variable's value will be modified by different threads.
    // public static volatile boolean flag = false;
    public static boolean flag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true;
        });

        Thread t2 = new Thread(()->{
            while(!flag){
                // do nothing
            }
            System.out.println("Thread 2 is running");
        });
        t1.start();
        t2.start();
    }
}
