
public class generics {
    public static void main(String[] args) {
        Box<Integer> b1 = new Box<Integer>(10); // Type argument
        System.out.println(b1.getValue() + 5);

        Box<String> b2 = new Box<String>("Parth"); 
        System.out.println(b2.getValue() + " Jadhao");

        printFunction(10, "Hello"); // type inference : in this case the compiler is able to infer the type of the parameters based on the arguments passed to the method
    }

    // generic methods : methods that are able to handle multiple data types
    public static <T,U> void printFunction(T param1, U param2){
        System.out.println(param1 + " , " + param2);
    }
    
}

// generic class : class that is able to handle multiple data types
class Box<T>{
    private T value;

    Box(T value){
        this.value = value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return value;
    }
}