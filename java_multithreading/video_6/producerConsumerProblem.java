package video_6;


// Producer Consumer Problem is a classic example of a multi-process synchronization problem.
// It involves two types of processes, the producer and the consumer, which share a common,
// fixed-size buffer used as a queue. The producer's job is to generate data, put it into the buffer,
// and start again. At the same time, the consumer is consuming the data (i.e., removing it from the buffer),
// one piece at a time. The problem is to make sure that the producer won't try to add data into the buffer if it's full and that the consumer won't try to remove data from an empty buffer.
public class producerConsumerProblem {
    public static void main(String[] args) {
        Box box = new Box();

        Thread t1 = new Thread(()->{
            for(int i = 0 ; i <=20 ; i++){
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println("Thread unable to go into sleep state");
                }
                box.producer(i);
            }
        });
        Thread t2 = new Thread(()->{
            for(int i = 0 ; i <=20 ; i++){
                try {
                    Thread.sleep(70);
                } catch (Exception e) {
                    System.out.println("Thread unable to go into sleep state");
                }
                box.consumer();
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
        item = value;
        flag = true;
        System.out.println("Producer produces " + item);
    }

    void consumer() {
        System.out.println("Consumer consumes " + item);
        item = null;
        flag = false;
    }
}
