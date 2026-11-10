package video2;

public class constructorCode {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.print();
    }
}

class Student {
    String name;
    int age;
    int rollNumber;
    String college;


    // constructor overloading
    Student(){
        // constructor chaining
        this("unknown",0,0,"unknown");
    }

    // constructor
    Student(String name, int age, int rollNumber, String college){
        this.name = name;
        this.age = age;
        this.rollNumber = rollNumber;
        this.college = college;
    }

    void markAttendence(){
        System.out.println("Attenance marked");
    }

    void print(){
        System.out.println("name : " + name +"\nage : " + age+"\nroll number : " + rollNumber+"\ncollege : " + college);
    }
}
