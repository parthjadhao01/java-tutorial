package video3;

public class callByRefefrece {
    public static void main(String[] args) {
        Random r = new Random(21, 42);
        System.out.println("x : " + r.x + " y : " + r.y);
        addTen(r);
        System.out.println("x : " + r.x + " y : " + r.y);
    }  
    
    static void addTen(Random r){
        r.x += 10;
        r.y += 10;
    }
}

class Random{
    int x;
    int y;

    Random(int x , int y){
        this.x = x;
        this.y = y;
    }
    
    
}