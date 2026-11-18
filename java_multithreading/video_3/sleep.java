package video_3;

public class sleep {
    public static void main(String[] args){
        System.out.println("Main thread Running");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Main thread Running after 2 seconds");
        System.out.println("Main thread Terminating");
    }
}
