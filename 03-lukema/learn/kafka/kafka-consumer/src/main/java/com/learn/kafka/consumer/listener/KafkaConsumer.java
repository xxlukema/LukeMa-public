package com.learn.kafka.consumer.listener;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.learn.kafka.consumer.model.User;


@Service
public class KafkaConsumer {

    @KafkaListener(topics = "Kafka_Example, Kafka_Example_2", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "Kafka_Example_json", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(User user) {
        System.out.println("Consumed JSON Message: " + user);
    }

    /**
     * @Header
     */
    @KafkaListener(topics = "topicName")
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + "from partition: " + partition);
    }

    /**
     * Partition
     * 
     * If the initialOffset has been sent to 0 in this listener, all the previously consumed messages from partitions 0
     * and three will be re-consumed every time this listener is initialized.
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName", partitionOffsets = { @PartitionOffset(partition = "0", initialOffset = "0"),
            @PartitionOffset(partition = "3", initialOffset = "0") }), containerFactory = "partitionsKafkaListenerContainerFactory")
    public void listenToPartition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + "from partition: " + partition);
    }

    /**
     * Partition without offset
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName", partitions = { "0", "1" }))
    public void listenToPartitionNoOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + "from partition: " + partition);
    }
}
