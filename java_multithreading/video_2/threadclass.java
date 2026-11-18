package video_2;

public class threadclass{
    public static void main(String[] args) {
        myThread t1 = new myThread();
        t1.start();
    }
}

class myThread extends Thread{
    @Override 
    public void run(){
        // logic thread should execute
        System.out.print("Thread is running");
    }
}

/*
t1.start() --> JVM as OS to create a new thread  --> thread get Stack/PC space
Thread executes run method

*/