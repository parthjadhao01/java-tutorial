package video_5;

public class customObjectLock {
    public static void main(String[] args) {
        Bank b1 = new Bank();
        Thread t1 = new Thread(()-> {b1.deposit();});
        Thread t2 = new Thread(()->{b1.withdraw();});
        t1.start();
        t2.start();
    }
}

class Bank {
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void deposit() {
        synchronized (lock1) {
            System.out.println("Depositing the cash");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println("Depositing the cash completed");
        }
    }

    public void withdraw() {
        synchronized (lock2) {
            System.out.println("Withdraw the cash");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println("Withdrawing the cash completed");
        }
    }
}