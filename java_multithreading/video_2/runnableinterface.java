package video_2;

public class runnableinterface {
    public static void main(String[] args){
        myRunnable r1 = new myRunnable();
        Thread t1 = new Thread(r1);
        t1.start();
    }
}

// Thread using runnable interface
class myRunnable implements Runnable {
    @Override 
    public void run(){
        System.out.print("Thread is running");
    }
}
