package com.example.jmstemplate.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @fileName：ConsumerService
 * @createTime：2019-7-19 17:15
 * @author：XSH
 * @version：
 * @description：
 */

@Component
public class QueueConsumer {

//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;

    // 使用JmsListener配置消费者监听的队列
    @JmsListener(destination = "myQueue")
    public void handleMessage(String msg) {
        System.out.println("服务端成功接收queue消息成功：" + msg);
    }

    /*
    @JmsListener(destination = "myQueue")
    // SendTo 会将此方法返回的数据, 写入到 OutQueue 中去.
    @SendTo("outQueue")
    public String handleMessage2(String msg) {
        System.out.println("成功接受msg" + msg);
        return "成功接受msg" + msg;
    }
    */
}
