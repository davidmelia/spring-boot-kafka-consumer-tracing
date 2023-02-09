# spring-boot-kafka-consumer-tracing
spring-boot-kafka-consumer-tracing

1) create the test topic in kafka: kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

2) hit http://localhost:8080/sendMessage

3) you will get


2023-02-09T09:22:16.844Z  INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=] 36173 --- [-f567995d3b2a-1] c.e.demo.consumer.TestMessagesConsumer   : Reactor Kafka Binder: This log statement does not have the trace id

2023-02-09T09:22:16.844Z  INFO [aid=spring-boot-kafka-consumer-tracing,tid=63e4bb48fde4e7bad38152c80451f3e1,sid=7a6ca04b1324a87c,cusname=My Name] 36173 --- [container-0-C-1] c.e.demo.consumer.TestMessagesConsumer   : Kafka Binder: This log statement has the trace id

2023-02-09T09:22:16.854Z  INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=] 36173 --- [     parallel-4] c.e.demo.consumer.TestMessagesConsumer   : Reactor Kafka Binder: This log statement does not have the trace id

2023-02-09T09:22:16.856Z  INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=] 36173 --- [     parallel-5] c.e.demo.consumer.TestMessagesConsumer   : Kafka Binder: This log statement does not have the trace id
