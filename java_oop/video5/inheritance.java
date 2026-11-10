package video5;

public class inheritance {
    public static void main(String[] args) {
        EngineeringStudent s1 = new EngineeringStudent(); // child class object
        s1.name = "Parth"; // student class property | parent class property
        s1.age = 22; // student class property | parent class property
        s1.markeAttendance(); // student class property | parent class property
        s1.markedLabAttendance(); // engineeringStudent class property | child class property
    }
}

class Student{
    String name;
    int age;

    void markeAttendance(){
        System.out.println("Marked Student Attendance");
    }
}

class EngineeringStudent extends Student{
    void markedLabAttendance(){
        System.out.println("Marked Engineering Student Labe Attendance");
    }
}