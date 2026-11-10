package video3;

public class copy {
    public static void main(String[] args) {
        Random r1 = new Random(5,10);
        Random r2 = new Random(r1); // deep copy
        Random r3 = r1; // shallow copy
        r1.print();
        r2.print();
        r3.print();
    }
}

class Random{
    int x;
    int y;

    Random(int x , int y){
        this.x = x;
        this.y = y;
    }

    Random(Random r){
        this.x = r.x;
        this.y = r.y;
    }
    
    void print(){
        System.out.println("x : " + x + " y : " + y);
    }
}

