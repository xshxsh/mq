package com.example.kafka.cosumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author 谢仕海
 * @date : 2019-10-29 11:37
 * description: kafka消费者
 */

@Component
public class KafkaConsumer {

    /**
     * topic 主题
     * containerFactory 生产者容器工厂
     * idIsGroup 为true则消费者id等于消费组id，为false则自动生成id
     *
     * @param records
     */
    @KafkaListener(topics = "#{'${kafka.consumer.topic}'}", containerFactory = "myKafkaListenerContainerFactory", idIsGroup = false)
    public void listen1(ConsumerRecords<?, String> records, Acknowledgment ack) {
        for (ConsumerRecord<?, String> record : records) {
            System.out.println(record.topic() + "," + record.key() + "," + record.value());
        }
        ack.acknowledge();
    }

    /**
     * 消费指定主题和分区的消息
     * containerFactory 生产者容器工厂
     * id 消费者id
     * idIsGroup 为true则消费者id等于消费组id，为false则自动生成id
     * TopicPartition: topic:需要监听的Topic的名称，partitions:需要监听Topic的分区id，partitionOffsets:可以设置从某个偏移量开始监听
     * partitionOffsets: 指定从分区的那个位移开始消费
     *
     * @param records
     */
        /*
    @KafkaListener(containerFactory = "myKafkaListenerContainerFactory", id = "listen2", idIsGroup = false,
            topicPartitions = {
                    @TopicPartition(topic = "#{'${kafka.consumer.topic1}'}", partitions = {"0", "1"}),
                    @TopicPartition(topic = "#{'${kafka.consumer.topic2}'}", partitions = {"2", "4"},
                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
            }
    )
         */
    public void listen2(ConsumerRecords<?, String> records, Acknowledgment ack) {
        for (ConsumerRecord<?, String> record : records) {
            System.out.println(record.topic() + "," + record.key() + "," + record.value());
        }
        ack.acknowledge();
    }

}
