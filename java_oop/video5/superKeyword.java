package video5;

public class superKeyword {
    public static void main(String[] args) {
        EngineeringStudents s1 = new EngineeringStudents("Parth",22);
        s1.studnetDetail();
    }
}

class Students{
    String name;
    int age;

    Students(String name, int age){
        this.name = name;
        this.age = age;
    }

    void markeAttendance(){
        System.out.println("Marked Student Attendance");
    }
}

class EngineeringStudents extends Students{

    EngineeringStudents(String name,int age){
        super(name,age);
    }

    void markedLabAttendance(){
        System.out.println("Marked Engineering Student Labe Attendance");
    }

    void studnetDetail(){
        System.out.println("Name : "+super.name);
        System.out.println("Age : "+super.age);
    }
}