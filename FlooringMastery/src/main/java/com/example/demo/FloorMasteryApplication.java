package com.example.demo;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.demo.controller.OrderingController;

@SpringBootApplication
public class FloorMasteryApplication {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SpringApplication.run(FloorMasteryApplication.class, args);
		
        ApplicationContext appContext
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        OrderingController controller = appContext.getBean("controller", OrderingController.class);
        Scanner keyboard = new Scanner(System.in);
        
        controller.setKeyboard(keyboard);
        controller.run();
        
        keyboard.close();
	}

}
