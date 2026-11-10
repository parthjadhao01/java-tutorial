public class upcastingdowncasting {
    public static void main(String[] args) {
        System.out.println("Upcasting Result");
        String s = "Parth";
        Object obj = s; // parent class (obj) are casted to child class (String)
        System.out.println(obj);
        System.out.println("Downcasting Result");
        Object obj1 = "Jadhao";
        String s1 = (String) obj1; // Downcasting
        System.out.println(s1);
    }
}
