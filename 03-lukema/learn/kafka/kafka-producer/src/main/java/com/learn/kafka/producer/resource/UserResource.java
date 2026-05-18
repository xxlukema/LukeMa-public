package com.learn.kafka.producer.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.kafka.producer.model.User;


@RestController
@RequestMapping("kafka")
public class UserResource {

    private static final String TOPIC = "Kafka_Example";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    /**
     * Blocking
     */
    @GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {

        kafkaTemplate.send(TOPIC, new User(name, "Technology", 12000L));

        return "Published successfully";
    }

    /**
     * @EnableAsync
     * 
     * Non-Blocking
     */
    @Async
    public void sendMessage(String name) {

        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(TOPIC, new User(name, "Technology", 12000L));

        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {

            @Override
            public void onSuccess(SendResult<String, User> result) {
                System.out.println("Sent message=[" + name + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + name + "] due to : " + ex.getMessage());
            }
        });
    }

    /**
     * TODO: Send message with KEY --- guarantees order within a partition
     * 
     * If you require that messages with the same key (for instance, a unique id) are always seen in the correct order,
     * attaching a key to messages will ensure messages with the same key always go to the same partition in a topic.
     * Kafka guarantees order within a partition, but not across partitions in a topic.
     * 
     * If there is no key provided, then Kafka will partition the data randomly in a round-robin fashion.
     * 
     * In real-life use cases, the key of a Kafka message can have a huge influence on your performance and clarity of
     * your business logic.
     * 
     * A key can for example be used naturally for partitioning your data. As you can control your consumers to read
     * from particular partitions this could serve as an efficient filter. Also, the key can include some meta data on
     * the actual value of the message that helps you control the subsequent processing. Keys are usually smaller then
     * values and it is therefore more convenient to parse a key instead of the whole value.
     * 
     * The key with a message is basically sent to get the message ordering for a specific field:
     * (1) If key=null, data is sent round-robin (to a different partition and to a different broker in a distributed env.
     *     and of course to the same topic.).
     * (2) If a key is sent, then all messages for that key will always go to the same partition.
     * 
     * Explain and example:
     * 
     * key can be any string or integer, etc.. take an example of an integer employee_id as key.
     * So emplyee_id 123 will always go to partition 0, employee_id 345 will always go to partition 1.
     * This is decided by the key hashing algorithm which depends on the number of partitions. If you don't send any
     * key then the message can go to any partition using a round-robin technique.
     * 
     */
    // method

    /**
     * TODO: Send message with partition id
     */
    // method

}
