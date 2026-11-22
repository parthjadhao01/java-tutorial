package video_7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class reentrantLock {
    public static void main(String[] args) {
        LockExample lex = new LockExample();

        Thread t1 = new Thread(()->{lex.method1();});
        Thread t2 = new Thread(()->{lex.method1();});
        Thread t3 = new Thread(()->{lex.method1();});

        t1.start();
        t2.start();
        t3.start();
    }
}

class LockExample{
    Lock lock = new ReentrantLock();

    public void method1(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "entered");

            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            
            System.out.println(Thread.currentThread().getName() + "exited");
        } finally {
            lock.unlock();
        }
    }
}
