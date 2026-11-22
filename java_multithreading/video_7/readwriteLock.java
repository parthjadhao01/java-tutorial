package video_7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// A simple example of read write lock
// Read write lock is a type of lock that allows multiple threads to read a shared resource simultaneously, but only one thread to write to the shared resource at a time.
// This allows for better performance in situations where there are many read operations and few write operations, as it allows multiple threads to read
public class readwriteLock {
    public static void main(String[] args) {
        sharedResource sr = new sharedResource();

        Thread r1 = new Thread(()->{sr.read();});
        Thread r2 = new Thread(()->{sr.read();});
        Thread r3 = new Thread(()->{sr.read();});

        Thread w1 = new Thread(()->{sr.write(10);});
        Thread w2 = new Thread(()->{sr.write(8);});
        Thread w3 = new Thread(()->{sr.write(4);});

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

    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock r1 = rwLock.readLock();
    Lock w1 = rwLock.writeLock();

    public int read() {
        r1.lock();
        try {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Read value : " + this.value);
            return this.value;
        } finally {
            r1.unlock();
        }
    }

    public void write(int newValue) {
        w1.lock();
        try {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Write value : " + newValue);
            this.value = newValue;
        } finally {
            w1.unlock();
        }
    }

}
