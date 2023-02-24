package com.example.demo;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

  @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Map> sendMessage() {
    return Mono.just(Map.of());
  }

}
