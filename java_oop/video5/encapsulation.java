package video5;

public class encapsulation {
    public static void main(String[] args){
        BankAccount b1 = new BankAccount();
        double balnace = b1.checkBalance();
        System.out.println("Balance : "+balnace);
        b1.deposit(42324);
        System.out.println("Balance after deposit : "+b1.checkBalance());
        b1.withDraw(10000);
        System.out.println("Balance after withdraw : "+b1.checkBalance());
        
    }
}

class BankAccount{
    // keeping data restricted by not giving direct access
    private double balance;

    // setter function updating class data
    public void withDraw(double amount){
        this.balance = this.balance - amount;
    }

    public void deposit(double amount){
        this.balance = this.balance + amount;
    }

    // getter function for fetchign class data
    public double checkBalance(){
        return this.balance;
    }

}
