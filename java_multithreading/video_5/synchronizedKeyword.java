package video_5;

public class synchronizedKeyword {
    public static void main(String[] args) {
        Counter counter = new Counter();
       
        Thread t1 = new Thread(()->{
            for(int i = 0 ; i < 10000; i++){
                counter.increment();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i = 0 ; i < 10000; i++){
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        

        System.out.println("Count : " + counter.count);

    }
}
class Counter {
    public int count = 0;

    // synchronized keyword is used to indicate that a method can be accessed by only one thread at a time.
    // critical section is a part of the code that can be accessed by only one thread at a time. In this example, the increment() method is a critical section because it can be accessed by only one thread at a time.
    public synchronized void increment(){
        this.count++;
    
    }
}