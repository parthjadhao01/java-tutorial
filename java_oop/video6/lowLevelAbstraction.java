package video6;

class abstraction{
    public static void main(String[] args) {
        Cars c = new Cars();
//      this is low level Absctraction hiding implementation of start and accelerate
        c.Start();
        c.Accelarate();
    }
}

class Cars {
    void Start(){
        System.out.println("Car started");
    }

    void Accelarate(){
        System.out.println("Car Accelarated");
    }
}