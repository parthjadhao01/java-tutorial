public class genericsBonds2 {
    public static void main(String[] args){
        // Box <Dog> b1 = new Box<>() // This will give an error because Dog is not a subclass of Swimmable
        Box<Fish> b2 = new Box<Fish>(); // This will work because Fish is a subclass of Animal and implements Swimmable
        Fish f = new Fish();
        f.swim();
    }
}

class Box<T extends Animal & Swimmable>{ // This is a generic class that can only accept types that are subclasses of Animal and implement the Swimmable interface
    private T value;

    Box(){
    }

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return value;
    }

}

class Animal{
    void display(){
        System.out.println("Displaying a animal");
    }
}

class Dog extends Animal{

}

interface Swimmable{
    void swim();
}

class Fish extends Animal implements Swimmable{
    @Override 
    public void swim(){
        System.out.println("Fish is swimming");
    }

}


