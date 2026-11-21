package video_6;

// Wait and Notify is a mechanism that allows threads to communicate with each other about the availability of resources.
// It is used to synchronize the execution of threads and ensure that they work together efficiently.
// The wait() method is used to pause the execution of a thread until it is notified by another thread,
// while the notify() method is used to wake up a waiting thread.
public class waitAndNotify {
    public static void main(String[] args) {
        Box box = new Box();

        Thread t1 = new Thread(()->{
            for(int i = 0 ; i <=20 ; i++){
                try {
                    Thread.sleep(100);
                    box.producer(i);
                } catch (Exception e) {
                    // System.out.println("Thread unable to go into sleep state"+e.getMessage());
                }
            }
        });
        Thread t2 = new Thread(()->{
            for(int i = 0 ; i <=20 ; i++){
                try {
                    Thread.sleep(100);
                    box.consumer();
                } catch (Exception e) {
                    // System.out.println("Thread unable to go into sleep state : "+e.getMessage());
                }
            }
        });

        t1.start();
        t2.start();
    }
}

class Box {
    volatile Integer item;
    volatile Boolean flag = false;

    void producer(int value) {
        while(flag){
            try {
                wait();
            } catch (Exception e) {
                // System.out.println("Error in producer");
            }
        }
        item = value;
        flag = true;
        System.out.println("Producer produces " + item);
        notify();
    }

    void consumer() {
        while(!flag){
            try {
                wait();
            } catch (Exception e) {
                // System.out.println("Error in consumer");
            }
        }
        System.out.println("Consumer consumes " + item);
        item = null;
        flag = false;
        notify();
    }
}
