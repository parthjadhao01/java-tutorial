package video2;
import java.util.ArrayList;
import java.util.List;


public class wildcard {
    public static void main(String[] args){
        // List<Dog> dogs = new ArrayList<>();
        // dogs.add(new Dog());
        // dogs.add(new Dog());

        // fun(dogs); // This will give an error because List<Dog> is not a subclass of List<Animal> even though Dog is a subclass of Animal. This is because generics are invariant in Java.

        List<video2.Animal> animals = new ArrayList<>();
        animals.add(new video2.Animal());
        animals.add(new video2.Animal());

        fun(animals); // This will work because List<Animal> is a subclass of List<Animal>
    }

    // static void fun(List<Animal> list){
    //     for(Animal a : list){
    //         a.eat();
    //     }
    // }

    // wildcard generic method : A wildcard generic method is a method that can accept any type of generic class. The wildcard is represented by the question mark (?) symbol. The wildcard can be used in the method parameter to accept any type of generic class.
    static void fun(List<?> list){
        
        // Object obj = list.add(new Dog()); // write operation is not allowed in wildcard generic method because we don't know the type of the generic class. We can only access the methods of the Object class because all classes in Java are subclasses of the Object class.

        for(Object a : list){
            // only read operation are allowed
            System.out.println(a.getClass().getName()); // in wildcard generic method, we can only access the methods of the Object class because we don't know the type of the generic class. We can only access the methods of the Object class because all classes in Java are subclasses of the Object class.
        }


    }
}

