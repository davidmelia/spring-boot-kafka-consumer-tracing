package com.example.demo.consumer;

import io.micrometer.observation.ObservationRegistry;
import java.time.Duration;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class TestMessagesConsumer {
  
  @Autowired
  private ObservationRegistry observationRegistry;
  
  @Bean
  Function<Flux<String>, Mono<Void>> testMessagesKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Kafka Binder: This log statement has the trace id");
      return Mono.just("OK").delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("Kafka Binder: This log statement also has the trace id in micrometer 1.10.8 but NOT in micrometer 1.10.9"));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).tap(Micrometer.observation(observationRegistry)).then();
  }

}
