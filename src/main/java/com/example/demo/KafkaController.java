package com.example.demo;

import static org.springframework.http.ResponseEntity.ok;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.BaggageInScope;
import io.micrometer.tracing.Tracer;
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

  private final Tracer tracer;
  private final StreamBridge streamBridge;

  @GetMapping(value = "/sendMessage", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Map>> sendMessage() {

    return Mono.deferContextual(contextView -> {
      try (ContextSnapshot.Scope scope = ContextSnapshot.setThreadLocalsFrom(contextView,
          ObservationThreadLocalAccessor.KEY)) {
        try (BaggageInScope baggage = this.tracer.createBaggage("Customer-Name", "My Name").makeCurrent()) {
          log.info("enter sendMessage():: {}");
          Object kafkaEvent = MessageBuilder
              .withPayload(Map.of("Key", String.format("Value @ %s", ZonedDateTime.now(ZoneOffset.UTC))))
              .setHeaderIfAbsent(KafkaHeaders.KEY, UUID.randomUUID().toString()).build();
          log.info("Sending message to binding = {}", kafkaEvent);

          if (streamBridge.send("test-out-0", kafkaEvent)) {
            log.info("Message sent to binding = {}", kafkaEvent);
            return Mono.just(ok(Map.of("event sent=",kafkaEvent)));
          }  else {
            log.error("Error occurred while sending message = {} to the binding.", kafkaEvent);
            return Mono.error(new RuntimeException("event publishing failed"));
          }


        }
      }
    });
  }


}