package video_3;

public class yieldMethod {
    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            for(int i=0; i<5; i++){
                System.out.println("Thread 1");
                Thread.yield();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i=0; i<5; i++){
                System.out.println("Thread 2");
            }
        });

        t1.start();
        t2.start();
    }
}

/*
Thread.yield() method is used to pause the currently executing thread and allow other threads to execute.
It is a static method of the Thread class. When a thread calls yield(),
it moves from the RUNNING state to the RUNNABLE state, allowing other threads of the same priority to execute.
However, it does not guarantee that the yielding thread will be paused for a specific amount of time or that it will be resumed immediately after yielding.
The actual behavior of yield() depends on the thread scheduler and the operating system's thread management.
*/
