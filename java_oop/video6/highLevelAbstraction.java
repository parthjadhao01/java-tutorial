package video6;

public class highLevelAbstraction{
    public static void main(String[] args) {
        car c1 = new electricCar();
        car c2 = new fuleCar();

        c1.Accelrate();
        c1.Start();
        c1.handbrekengage();

        c2.Accelrate();
        c2.Start();
        c2.handbrekengage();

    }
}

High levle abstraction withough Abstract class
class car{
//    this delcaratin is useless to solve this java introduced abstract class
//    void start();
//    void Start(){
//        System.out.println("Car started");
//    }
    void Accelrate(){
        System.out.println("Car Accelrated");
    }
    void handbrekengage(){
        System.out.println("Handbreak Engage");
    }
}

class electricCar extends car {
//    this is high level Abstraction
    void start(){
        System.out.println("Electric car started");
    }
    void accelrated(){
        System.out.println("Electric car Accelerated");
    }
}

class fuleCar extends car{
//    this is high level Abstraction
    void start(){
        System.out.println("Fuel car started");
    }

    void accelrated(){
        System.out.println("Fuel car Accelerated");
    }

}

