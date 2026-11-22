package video_7;

import java.util.concurrent.locks.StampedLock;

// A simple example of stamped lock
// Stamped lock is a type of lock that allows for optimistic reading,
// which means that a thread can read a shared resource without acquiring a lock,
// as long as no other thread is writing to the resource. If another thread is writing to the resource,
// the reading thread will be blocked until the writing thread releases the lock.
// This allows for better performance in situations where there are many read operations and few write operations,
// as it allows multiple threads to read the shared resource simultaneously without blocking each other.
public class stampedLock {
    public static void main(String[] args) {
        sharedResource sr = new sharedResource();

        Thread r1 = new Thread(() -> {
            sr.read();
        });
        Thread r2 = new Thread(() -> {
            sr.read();
        });
        Thread r3 = new Thread(() -> {
            sr.read();
        });

        Thread w1 = new Thread(() -> {
            sr.write(10);
        });
        Thread w2 = new Thread(() -> {
            sr.write(8);
        });
        Thread w3 = new Thread(() -> {
            sr.write(4);
        });

        r1.start();
        r2.start();
        r3.start();

        w1.start();
        w2.start();
        w3.start();
    }
}

class sharedResource {
    private int value = 0;

    StampedLock stampedLock = new StampedLock();

    public int read() {
        long stamp = stampedLock.tryOptimisticRead();

        int currentValue = value;
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        if (stampedLock.validate(stamp) == false) {
            // fallover logic
            // try pesimiestic read
            stamp = stampedLock.readLock();
            try {
                System.out.println(Thread.currentThread().getName() + " : Fallover logic");
                currentValue = value;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

        System.out.println(Thread.currentThread().getName() + " : Read value : " + currentValue);
        return currentValue;
    }

    public void write(int newValue) {
        long stamp = stampedLock.writeLock();
        try {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            System.out.println("Write value : " + newValue);
            this.value = newValue;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}