package video_2;
public class lambdaExpressInThread {
    
    public static void main(String[] args) {
        Thread t1 = new Thread(()->System.out.println("thread run"));
        t1.start();
    }
  
}
