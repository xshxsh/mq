package com.example.jmstemplate.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @fileName：TopicConsumer
 * @createTime：2019-7-22 14:59
 * @author：XSH
 * @version：
 * @description：
 */

@Component
public class TopicConsumer {

    // 使用JmsListener配置消费者监听的队列
    @JmsListener(destination = "myTopic")
    public void handleMessage1(String msg) {
        System.out.println("消费者1成功接收topic消息成功：" + msg);
    }

    // 使用JmsListener配置消费者监听的队列
    @JmsListener(destination = "myTopic")
    public void handleMessage2(String msg) {
        System.out.println("消费者2成功接收topic消息成功：" + msg);
    }
}
