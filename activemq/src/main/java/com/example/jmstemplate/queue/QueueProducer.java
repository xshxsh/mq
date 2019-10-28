package com.example.jmstemplate.queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @fileName：ProducerService
 * @createTime：2019-7-22 14:45
 * @author：XSH
 * @version：
 * @description：
 */

/**
 * 当一个消息生产者产生一个消息时，会把消息放入一个队列(Queue)中，然后消息消费者从Queue中读取消息，
 * 如果同时有多个消费者读取消息，ActiveMq保证消息只会被一个消费者读取到，消费者读取到消息之后需要向
 * ActiveMq发送一条确认信息，确认消息已经被接收，此时，队列(Queue)中的消息出队，没被消费的消息会一
 * 直存在MQ队列中直到MQ被关闭。
 */

@RestController
public class QueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("sendToQueue")
    public void sendMessage(String msg, HttpServletResponse response) throws IOException {

        //队列名称
        ActiveMQQueue queue = new ActiveMQQueue("myQueue");

        //发送消息
        jmsMessagingTemplate.convertAndSend(queue, msg);

        System.out.println("客户端发送消息成功");

        response.getWriter().write("success");
    }
}
