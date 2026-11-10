public class abstractclass{
    public static void main(String[] args) {

//        car c = new car(); // abstract class cannot be initiated
        car electric = new electricCar();
        electric.accelerate();
        electric.handbreakengage();
        electric.start();
    }
}

abstract class car{
    abstract void start();
    abstract void accelerate();
    void handbreakengage(){
        System.out.println("car handbreak engage");
    }
}

class electricCar extends car{

    @Override
    void accelerate(){
        System.out.println("electric car accelerate");
    }

    @Override
    void start(){
        System.out.println("electric car started");
    }
}

class fuelCars extends car{
    @Override
    void accelerate(){
        System.out.println("fuel car accelerate");
    }

    @Override
    void start(){
        System.out.println("fuel car started");
    }
}