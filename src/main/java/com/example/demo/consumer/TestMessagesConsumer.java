package com.example.demo.consumer;

import io.micrometer.observation.ObservationRegistry;
import java.time.Duration;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TestMessagesConsumer {
  
  @Autowired
  public ObservationRegistry or;

  @Bean
  public Function<Flux<String>, Mono<Void>> testMessagesReactorKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Reactor Kafka Binder: This log statement does not have the trace id");
      return Mono.just("OK").delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("Reactor Kafka Binder: This log statement does not have the trace id"));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).tap(Micrometer.observation(or)).then();
  }

  @Bean
  public Function<Flux<String>, Mono<Void>> testMessagesKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Kafka Binder: This log statement has the trace id");
      return Mono.just("OK").delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("Kafka Binder: This log statement also has the trace id"));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).tap(Micrometer.observation(or)).then();
  }

}
