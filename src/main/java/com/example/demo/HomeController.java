package com.example.demo;

import com.example.demo.mongo.CustomerRepository;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class HomeController {

  @Autowired
  private CustomerRepository repository;

  @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Map<Object, Object>> sendMessage() {
    log.info("has a trace id");
    return repository.findByFirstName("Alice").map(c -> {
      log.info("does not have a trace id");
      return Map.of(c.firstName, c.lastName);
    });


  }

}
