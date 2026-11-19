package video_5;

public class staticSynchronization {
    public static void main(String[] args) {
        Test t1 = new Test();

        Thread thread1 = new Thread(()->Test.m1());
        Thread thread2 = new Thread(()->Test.m2());
        // class level lock is not taken by m3 because it is called by object , object and class have different seperate locks, so m3 can be executed by thread3 even if thread1 and thread2 are executing m1 and m2 respectively.
        Thread thread3 = new Thread(()->t1.m3());

        thread1.start();
        thread2.start();
        thread3.start();
    }
    
}

class Test{
    // static synchronized take lock on class level, so only one thread can access the static synchronized method at a time.
    // If one thread is executing m1(), the other thread will have to wait until m1() is finished before it can execute m2(). 
    // In this example, both methods m1() and m2() are static synchronized, which means that only one thread can access either
    public static synchronized void m1(){
        System.out.println("Entered M1");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error in m1");
        }
        System.out.println("Ended M1");
    }

    public static synchronized void m2(){
        System.out.println("Entered M2");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error in m2");
        }
        System.out.println("Ended M2");
    }

    public void m3(){
        System.out.print("m3 processed");
    }
}
