package com.example.demo;

import com.example.demo.mongo.Customer;
import com.example.demo.mongo.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class MyApplication implements CommandLineRunner{
 
  @Autowired
  private CustomerRepository repository;
  public static void main(String[] args) {

    SpringApplication.run(MyApplication.class, args);
  }


  @PostConstruct
  public void init() {  
    Hooks.enableAutomaticContextPropagation();
  }


  @Override
  public void run(String... args) throws Exception {
    repository.deleteAll().block();

    // save a couple of customers
    repository.save(new Customer("Alice", "Smith")).block();
    repository.save(new Customer("Bob", "Smith")).block();
    
  }

}
