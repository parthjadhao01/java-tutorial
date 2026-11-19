package video_4;

// Race condition problem occurs when two or more threads try to access a shared resource simultaneously,
// and the final outcome depends on the order of execution of the threads.
//  In this example, both threads t1 and t2 are trying to increment the count variable of the Counter class, which can lead to inconsistent results.
public class raceconditonproblem {
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

    void increment(){
        this.count++;
    
    }
}