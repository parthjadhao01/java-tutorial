package video1;

// coding class and object in java
public class classObjectCode {
    public static void main(String[] args) {
        Student s1 = new  Student();
        s1.name = "parth";
        s1.age = 34;
        s1.rollNumber = 32;
        s1.college = "PCU";

        s1.print();

    }
}

class Student {
    String name;
    int age;
    int rollNumber;
    String college;

    void markAttendence(){
        System.out.println("Attenance marked");
    }

    void print(){
        System.out.println("name : " + this.name +"\nage : "+this.age+"\nroll number : "+this.rollNumber+"\ncollege : "+this.college);
    }
}