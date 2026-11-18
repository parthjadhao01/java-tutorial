package video_2;
public class threadLifeCycle {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        
        // Thread life cycle states
        Thread t1 = new Thread(()->{
            System.out.println("Name of thread : "+Thread.currentThread().getName() + " and state of thread : "+Thread.currentThread().getState());
            // Just checking state of main thread when t1 is running
            System.out.println("Name of thread : "+mainThread.getName() + " and state of thread : "+mainThread.getState());
        });
        
        // 1. New state of thread t1
        System.out.println("Name of thread : "+t1.getName() + " and state of thread : "+t1.getState());

        // 2. start method will change state of thread from NEW to RUNNABLE
        t1.start();
        
        System.out.println("Name of thread : "+t1.getName() + " and state of thread : "+t1.getState()); // RUNNABLE | TERMINATED

        // Putting main thread to sleep for 1 second so that t1 can complete its execution and change its state to TERMINATED
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Name of thread : "+t1.getName() + " and state of thread : "+t1.getState()); // TERMINATED

    }
}
