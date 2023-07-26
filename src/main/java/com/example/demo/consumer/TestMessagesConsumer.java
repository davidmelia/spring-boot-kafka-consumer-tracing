package com.example.demo.consumer;

import java.time.Duration;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class TestMessagesConsumer {
  
  @Bean
  Function<Flux<String>, Mono<Void>> testMessagesKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Kafka Binder: This log statement has the trace id");
      return Mono.just("OK").delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("Kafka Binder: This log statement also has the trace id in boot 3.0.8 but NOT in boot 3.0.9"));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).then();
  }

}
