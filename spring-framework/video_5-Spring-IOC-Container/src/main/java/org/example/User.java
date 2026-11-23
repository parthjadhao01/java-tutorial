package org.example;

import org.springframework.stereotype.Component;

//@Component -> cannot denote @Component anotation to such class which need some value in constructor
public class User {

    String username;
    int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
