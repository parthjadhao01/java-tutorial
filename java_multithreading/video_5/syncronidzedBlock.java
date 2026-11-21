package video_5;

public class syncronidzedBlock {
    public static void main(String[] args) {
        Test t1 = new Test();

        Thread thread1 = new Thread(()->{t1.m1();});
        Thread thread2 = new Thread(()->{t1.m2();});

        thread1.start();
        thread2.start();
    }
}

class Test{
    public void m1(){
        synchronized(this){
            System.out.println("Entered M1");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Error in m1");
            }
            System.out.println("Ended M1");
        }
    }

    public void m2(){
        synchronized(this){
            System.out.println("Entered M2");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Error in m2");
            }
            System.out.println("Ended M2");
        }
    }

    public void m3(){
        System.out.print("m3 processed");
    }
}