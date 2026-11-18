package video_3;

public class threadpriority {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println("Thread 1");
        });
        Thread t2 = new Thread(()->{
            System.out.println("Thread 2");
        });

        t1.start();
        t2.start();
        t1.setPriority(10);

        System.out.println(t1.getPriority());
    }
}

/* Thread priority is used to indicate the relative importance of a thread. The priority is an integer value between 1 and 10, where 1 is the lowest priority and 10 is the highest priority. The default priority of a thread is 5. 
there are three types of priority in java : 
1. MIN_PRIORITY : 1
2. NORM_PRIORITY : 5
3. MAX_PRIORITY : 10
Thread priority can be set using the setPriority() method of the Thread class. The thread scheduler uses the priority of a thread to determine the order in which threads are executed. However, it is important to note that thread priority is not a guarantee of execution order, and the actual behavior may vary depending on the thread scheduler and the operating system's thread management. 
*/