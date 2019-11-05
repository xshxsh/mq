package com.example.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢仕海
 * @date : 2019-10-21 10:00
 * description: kafka配置类
 */

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.consumer.max-poll-records}")
    private String maxPollRecords;


    /**
     * 消费者配置
     *
     * @return
     */
    public Map<String, Object> getConsumerPropertis() {
        Map<String, Object> properties = new HashMap<>(6);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        return properties;
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> myKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        /**
         * 手动提交偏移的模式
         * RECORD:每处理一条commit一次
         * BATCH(默认):每次poll的时候批量提交一次，频率取决于每次poll的调用频率
         * TIME :每次间隔ackTime的时间去commit
         * COUNT :累积达到ackCount次的ack去commit
         * COUNT_TIME:ackTime或ackCount哪个条件先满足，就commit
         * MANUAL:listener负责ack，但是背后也是批量上去
         * MANUAL_IMMEDIATE:listner负责ack，每调用一次，就立即commit
         */
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        //设置同步提交offset
        factory.getContainerProperties().setSyncCommits(true);
        DefaultKafkaConsumerFactory defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory(getConsumerPropertis());
        factory.setConsumerFactory(defaultKafkaConsumerFactory);
        //指定并发线程,根据分区数调整，一般来说消费者数大于或等于分区数(在同一个消费组时，一个分区同时只能被一个消费者消费)
        factory.setConcurrency(1);
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        return factory;
    }
}
