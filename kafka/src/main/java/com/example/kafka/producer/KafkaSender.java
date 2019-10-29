package com.example.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 谢仕海
 * @date : 2019-9-29 14:30
 * description:
 */

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息到kafka所有分区
    //定时发送
//    @Scheduled(cron = "*/1 * * * * ?")
    public void sendMessage() {
        kafkaTemplate.send("AFC-GATE-FLOW", "要发送的消息");
    }

    //发送消息，并指定消息的key
    public void sendMessageOnKey() {
        kafkaTemplate.send("AFC-GATE-FLOW", "1", "要发送的消息");
    }

    //发送消息到指定分区，并设定key
    public void sendMessageToPartition() {
        kafkaTemplate.send("AFC-GATE-FLOW", 1, "1", "要发送的消息");
    }


}
