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

      log.info("This log statement has the trace id");
      return Mono.just("OK").doOnNext(t -> log.info("This log statement also has the trace id")).delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("This log statement does not have the trace ID"));

    }, 1).onErrorResume(ex -> {
      log.info("Error", ex);
      return Mono.empty();
    }).then();
  }
  
}
