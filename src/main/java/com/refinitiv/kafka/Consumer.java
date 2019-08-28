package com.refinitiv.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Consumer {

    private Logger logger = LoggerFactory.getLogger("Consumer.LOG");
    private final String server, group, topic;

    public Consumer(String server, String group, String topic) {
        this.server = server;
        this.group = group;
        this.topic = topic;
    }

    public void run(){
        CountDownLatch latch = new CountDownLatch(1);

        ConsumerRunnable consumerRunnable = new ConsumerRunnable(server, group, topic, latch);
        Thread t = new Thread(consumerRunnable);

        t.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumerRunnable.shutDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("exited OK");
        }));
    }

    private Properties getProps(String server, String group){
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return properties;
    }

    private class ConsumerRunnable implements Runnable{
        private CountDownLatch countDownLatch;
        private KafkaConsumer<String, String> consumer;

        private ConsumerRunnable(String server, String group, String topic, CountDownLatch latch) {
            this.countDownLatch = latch;

            Properties p = getProps(server, group);
            consumer = new KafkaConsumer<String, String>(p);
            consumer.subscribe(Collections.singleton(topic));
        }

        public void shutDown(){
            consumer.wakeup();
        }

        @Override
        public void run() {
            try {
                do {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<String, String> record : records){
                        logger.info(record.key());
                        logger.info(record.value());
                        logger.info(String.valueOf(record.partition()));
                        logger.info(String.valueOf(record.offset()));
                    }
                } while (true);
            } catch (Exception e){
                logger.info("Wake up, Neo...");
            } finally {
                consumer.close();
                countDownLatch.countDown();
            }
        }
    }
}
