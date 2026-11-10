package video4;

public class finalCode {
    public static void main(String[] args){
        // student.College = "pcu"; // throw error reassigne value to final varirable
        s s1 = new s("narva", 20, 30);
        System.out.println(s1.name);
        System.out.println(s1.College);
        // s1.College = "pcu"; // throw error cannot reassign value to final varirable
    }
}
class s {
    String name;
    int age;
    int rollno;
    final static String College;

    s(String name, int age, int rollno) {
        this.name = name;
        this.age = age;
        this.rollno = rollno;
    }

    static {
        College = "parth";
    }
}