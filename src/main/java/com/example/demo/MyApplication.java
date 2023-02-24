package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {

  public static void main(String[] args) {

    SpringApplication.run(MyApplication.class, args);
  }


  @PostConstruct
  public void init() {
  
  }

}
