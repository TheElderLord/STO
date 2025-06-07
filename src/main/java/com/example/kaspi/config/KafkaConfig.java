package com.example.kaspi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic statusChangesTopic() {
        return TopicBuilder.name("status-changes")
                .partitions(3)
                .replicas(1)
                .build();
    }
}

