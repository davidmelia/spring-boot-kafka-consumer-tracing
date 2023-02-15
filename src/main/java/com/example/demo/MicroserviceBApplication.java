package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class MicroserviceBApplication {

  public static void main(String[] args) {

    SpringApplication.run(MicroserviceBApplication.class, args);
  }


  @PostConstruct
  public void init() {
    Hooks.enableAutomaticContextPropagation();
  }

}
