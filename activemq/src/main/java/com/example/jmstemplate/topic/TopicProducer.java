package com.example.jmstemplate.topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @fileName：TopicProducer
 * @createTime：2019-7-22 14:59
 * @author：XSH
 * @version：
 * @description：
 */

/**
 * 消息生产者（发布）将消息发布到 topic 中，同时有多个消息消费者（订阅）消费该消息。和点对点方式不同，
 * 发布到 topic 的消息会被所有订阅者消费。 当生产者发布消息，不管是否有消费者。都不会保存消息，
 * 所以一定要先有消息的消费者，后有消息的生产者。否则消息产生后会别立刻丢弃。启动项目的时候，先起消费者，再起生产者。
 */

@RestController
public class TopicProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("sendToTopic")
    public void sendMessage(String msg, HttpServletResponse response) throws IOException {

        //队列名称
        ActiveMQTopic topic = new ActiveMQTopic("myTopic");

        //发送消息
        jmsMessagingTemplate.convertAndSend(topic, msg);

        System.out.println("客户端发送消息成功");

        response.getWriter().write("success");
    }
}
