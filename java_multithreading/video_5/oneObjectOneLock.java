package video_5;

// Given below is to demonstrate synchronized keyword locks whole object.
// In this example, both methods m1() and m2() are synchronized,
// which means that only one thread can access either of the methods at a time.
// If one thread is executing m1(), the other thread will have to wait until m1() is finished before it can execute m2(). 
public class oneObjectOneLock {
    public static void main(String[] args) {
        Test t1 = new Test();

        Thread thread1 = new Thread(()->{t1.m1();});
        Thread thread2 = new Thread(()->{t1.m2();});

        thread1.start();
        thread2.start();
    }
}

// In java each object has a lock associated with it. 
// When a thread wants to access a synchronized method of an object,
// it must first acquire the lock for that object. If another thread already holds the lock,
// the new thread will be blocked until the lock is released.
// In this example, both methods m1() and m2() are synchronized,
// which means that only one thread can access either of the methods at a time.
// If one thread is executing m1(), the other thread will have to wait until m1() is finished before it can execute m2().
class Test{
    public synchronized void m1(){
        System.out.println("Entered M1");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error in m1");
        }
        System.out.println("Ended M1");
    }

    public synchronized void m2(){
        System.out.println("Entered M2");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error in m2");
        }
        System.out.println("Ended M2");
    }
}

// to avoid blocking whole object, we can use synchronized block instead of synchronized method. explained in code syncronidzedBlock.java