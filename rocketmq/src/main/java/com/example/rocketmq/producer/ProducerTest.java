package com.example.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;


/**
 * @fileName：SyncProducer
 * @createTime：2019-7-24 11:32
 * @author：XSH
 * @version：
 * @description：使用RocketMQ以三种方式发送消息：可靠的同步，可靠的异步和单向传输。
 */


public class ProducerTest {
    /**
     * 同步发送消息：可靠的同步传输用于广泛的场景，如重要的通知消息，短信通知，短信营销系统等。
     */
    public void SyncProducer() {
        // 初始化生产者分组
        DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
        // 设置实例名称
        producer.setInstanceName("producer");
        // 指定nameserver地址
        producer.setNamesrvAddr("localhost:9876");
        try {
            // 启动实例
            producer.start();
            for (int i = 0; i < 10; i++) {
                // 创建消息，指定主题名称，tag消息子类型（ 给broker做消息过滤）
                Message msg = new Message("MyTopic", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                // 发送消息，获取发送结果
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭连接
        producer.shutdown();
    }

    /**
     * 异步发送消息：异步传输通常用于响应时间敏感的业务场景
     */
    public void asyncProducer() {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        try {
            //Launch the instance.
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);
            for (int i = 0; i < 100; i++) {
                final int index = i;
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("MyTopic",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                                sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

    /**
     * 单向传输用于需要中等可靠性的情况，例如日志收集
     */
    public void OnewayProducer() {
        try {
            //Instantiate with a producer group name.
            DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
            // Specify name server addresses.
            producer.setNamesrvAddr("localhost:9876");
            //Launch the instance.
            producer.start();
            for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("MyTopic" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                producer.sendOneway(msg);

            }
            //Shut down once the producer instance is not longer in use.
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
