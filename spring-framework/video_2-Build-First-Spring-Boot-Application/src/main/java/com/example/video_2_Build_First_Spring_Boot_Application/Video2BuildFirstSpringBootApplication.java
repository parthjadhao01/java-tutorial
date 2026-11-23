package com.example.video_2_Build_First_Spring_Boot_Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Video2BuildFirstSpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(Video2BuildFirstSpringBootApplication.class, args);

		HelloController controller = new HelloController();
		String s = controller.hello();

		System.out.println(s);

	}

}
