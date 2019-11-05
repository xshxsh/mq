package com.example.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 谢仕海
 * @date : 2019-9-29 14:30
 * description: kafka生产者
 */

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka默认的发送消息策略：
     * The default partitioning strategy:
     * 如果记录中指定了分区，则使用它
     * If a partition is specified in the record, use it
     * 如果未指定分区但存在key，则根据键的哈希值选择一个分区
     * If no partition is specified but a key is present choose a partition based on a hash of the key
     * 如果没有分区或key，则以轮循的方式选择一个分区
     * If no partition or key is present choose a partition in a round-robin fashion
     **/

    //发送消息到kafka所有分区
    //定时发送
    //        @Scheduled(cron = "*/1 * * * * ?")
    public void sendMessage() {
        kafkaTemplate.send("myTopic", "要发送的消息");
    }

    //发送消息，并指定消息的key
    public void sendMessageOnKey() {
        kafkaTemplate.send("myTopic", "1", "要发送的消息");
    }

    //发送消息到指定分区，并设定key
    public void sendMessageToPartition() {
        kafkaTemplate.send("myTopic", 1, "1", "要发送的消息");
    }

    //通过回调函数判断生产者发送消息是否成功
    public void send() {
        kafkaTemplate.send("myTopic", "要发送的消息")
                .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        System.out.println("msg OK：" + result.toString());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        //此处可进行重试操作
                        System.out.println("msg send failed: ");
                    }
                });
    }

    //同步发送消息(以上均为异步发送)
    public void sendSync() throws InterruptedException, ExecutionException, TimeoutException {
        //当send方法耗时大于get方法所设定的参数时会抛出一个超时异常，但需要注意，
        // 这里仅抛出异常，消息还是会发送成功的，通常用于测试发送消息的时间
        kafkaTemplate.send("myTopic", "要发送的消息").get(10, TimeUnit.SECONDS);
    }

}
