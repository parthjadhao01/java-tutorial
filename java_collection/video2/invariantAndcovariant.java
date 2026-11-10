package video2;

import java.util.ArrayList;
import java.util.List;

public class invariantAndcovariant {
    public static void main(String[] args) {
        // Invariant in generics : Invariant means that if a class A is a subclass of class B, then Box<A> is not a subclass of Box<B>. This is because generics are invariant in Java.
       
       Animal a = new Dog(); // this is allowed in java where its not generic type because Dog is a subclass of Animal
       a.eat();
       a.walk();

        List<Dog> dogs = new ArrayList<>();
       // List<Animal> animals = dogs; // This will give an error because List<Dog> is not a subclass of List<Animal>


    //    Arrays are covariant in Java : Covariant means that if a class A is a subclass of class B, then A[] is a subclass of B[]. This is because arrays are covariant in Java.
       Dog[] dog = new Dog[5];
       Animal[] animal = dog;

       animal[0] = new Dog();
       animal[1] = new Dog();
       animal[2] = new Dog();
       animal[3] = new Dog();
       animal[4] = new Animal(); // This will give an ArrayStoreException at runtime because we are trying to store an Animal object in a Dog array
    }    
}


class Animal{
    void eat(){
        System.out.println("Animal is eating");
    }

    void walk(){
        System.out.println("Animal is walking");
    }
}

class Dog extends Animal{
    void bark(){
        System.out.println("Dog is barking");
    }
}
