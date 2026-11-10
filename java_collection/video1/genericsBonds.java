public class genericsBonds {
    public static void main(String[] args) {
        Box<Integer> b1 = new Box<Integer>(10); // Type argument
        System.out.println(b1.getValue() + 5);

        Box<Double> b2 = new Box<Double>(10.5); 
        System.out.println(b2.getValue() + 5.5);

        // Box<String> b3 = new Box<String>("Parth"); // This will give an error because String is not a subclass of Number
    }
}

class Box<T extends Number>{
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