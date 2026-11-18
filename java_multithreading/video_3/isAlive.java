package video_3;

public class isAlive {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Thread is alive status : " + t1.isAlive()); // false because thread is not started yet

        t1.start();
        System.out.println("Thread is alive status : " + t1.isAlive()); // true because thread is started and running

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread is alive status : " + t1.isAlive()); // false because thread is completed execution
    }
}

/* isAlive() method is used to check if a thread is currently alive or not.
   It returns true if the thread is alive, and false otherwise. */