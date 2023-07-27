package com.example.demo;

import static org.springframework.http.ResponseEntity.ok;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KafkaController {

  private final StreamBridge streamBridge;

  @GetMapping(value = "/sendMessage", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Map>> sendMessage() {

    log.info("enter sendMessage():: {}");
    Object kafkaEvent =
        MessageBuilder.withPayload(Map.of("Key", String.format("Value @ %s", ZonedDateTime.now(ZoneOffset.UTC)))).setHeaderIfAbsent(KafkaHeaders.KEY, UUID.randomUUID().toString()).build();
    log.info("Sending message to binding = {}", kafkaEvent);

    if (streamBridge.send("test-out-0", kafkaEvent)) {
      log.info("Message sent to binding = {}", kafkaEvent);
      return Mono.just(ok(Map.of("event sent=", kafkaEvent)));
    } else {
      log.error("Error occurred while sending message = {} to the binding.", kafkaEvent);
      return Mono.error(new RuntimeException("event publishing failed"));
    }

  }

  
  @GetMapping(value = "/threadContextPreserved", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Map<String, String>>> stuff() {
    log.info("This log statement has the trace id");
    return Mono.just(ok(Map.of("event sent=", "dave"))).delayElement(Duration.ofMillis(10)).doOnSuccess(r -> log.info("This log statement alsos has the trace id"));


  }

}
