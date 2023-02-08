package com.example.demo.consumer;

import java.time.Duration;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TestMessagesConsumer {

  @Bean
  public Function<Flux<String>, Mono<Void>> testMessagesReactorKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Reactor Kafka Binder: This log statement has the trace id ={}", event);
      return Mono.just("OK").delayElement(Duration.ofSeconds(1)).doOnSuccess(r -> log.info("Reactor Kafka Binder: This log statement does not have the trace id {}", r));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).then();
  }

  @Bean
  public Function<Flux<String>, Mono<Void>> testMessagesKafkaBinder() {
    return events -> events.flatMapSequential(event -> {

      log.info("Kafka Binder: This log statement has the trace id ={}", event);
      return Mono.just("OK").delayElement(Duration.ofSeconds(1)).doOnSuccess(r -> log.info("Kafka Binder: This log statement does not have the trace id {}", r));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).then();
  }

}
