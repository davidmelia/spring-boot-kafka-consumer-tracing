# spring-boot-kafka-consumer-tracing
spring-boot-kafka-consumer-tracing

1) create the test topic in kafka: kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

2) hit http://localhost:8080/sendMessage

3) you will get


INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=][0;39m [35m95615[0;39m [2m---[0;39m [2m[-513d531fe388-1][0;39m [36mc.e.demo.consumer.TestMessagesConsumer  [0;39m [2m:[0;39m Reactor Kafka Binder: This log statement has the trace id

INFO [aid=spring-boot-kafka-consumer-tracing,tid=63e3ab1ea59c918915034a79da332502,sid=e05816344a6df6d5,cusname=My Name][0;39m [35m95615[0;39m [2m---[0;39m [2m[container-0-C-1][0;39m [36mc.e.demo.consumer.TestMessagesConsumer  [0;39m [2m:[0;39m Kafka Binder: This log statement has the trace id

INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=][0;39m [35m95615[0;39m [2m---[0;39m [2m[     parallel-4][0;39m [36mc.e.demo.consumer.TestMessagesConsumer  [0;39m [2m:[0;39m Kafka Binder: This log statement does not have the trace id

INFO [aid=spring-boot-kafka-consumer-tracing,tid=,sid=,cusname=][0;39m [35m95615[0;39m [2m---[0;39m [2m[     parallel-4][0;39m [36mc.e.demo.consumer.TestMessagesConsumer  [0;39m [2m:[0;39m Reactor Kafka Binder: This log statement does not have the trace id
