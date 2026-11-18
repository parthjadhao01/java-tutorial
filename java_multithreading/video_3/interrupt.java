package video_3;

public class interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("Thread is running");
            }
        });
        t1.start();
        Thread.sleep(2000);
        t1.interrupt(); // interrupt method will set the interrupt flag of thread t1 to true
    }
}

/*
Thread.interrupt() method is used to interrupt a thread that is currently in a blocked or waiting state. 
When a thread is interrupted, it will throw an InterruptedException if it is in a blocking operation (like sleep, wait, or join).
If the thread is not in a blocking operation, the interrupt flag will be set, and the thread can check this flag using the isInterrupted() method to determine if it has been interrupted.
interrupted() method is a static method of the Thread class that checks if the current thread has been interrupted.
It returns true if the thread has been interrupted, and false otherwise.
*/