package video4;

public class staticCode {
    public static void main(String[] args) {
        student.College = "PCU";
        student s1 = new student("parth", 10, 10);
        student s2 = new student("siya", 20, 40);
        System.out.println(
                "name : " + s1.name + " age : " + s1.age + "roll no : " + s1.rollno + " college : " + student.College);
        System.out.println(
                "name : " + s2.name + " age : " + s2.age + "roll no : " + s2.rollno + " college : " + s2.College);
    }
}

class student {
    String name;
    int age;
    int rollno;
    static String College;

    student(String name, int age, int rollno) {
        this.name = name;
        this.age = age;
        this.rollno = rollno;
    }

    static {
        College = "parth";
    }
}