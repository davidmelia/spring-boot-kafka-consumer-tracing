package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Hooks;

@AutoConfigureObservability
@SpringBootTest
@AutoConfigureWebTestClient
class MyApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
  
	@Test
	void contextLoads() {
	  webTestClient.get().uri("/home").exchange().expectStatus().isOk();
	  Hooks.enableAutomaticContextPropagation();
      webTestClient.get().uri("/home").exchange().expectStatus().is5xxServerError();	   
	}
	

}
