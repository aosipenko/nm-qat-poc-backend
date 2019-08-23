import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {
   private KafkaProducer <String, String> producer;
   private Logger logger = LoggerFactory.getLogger("Producer.LOG");

   public Producer(String server){
       Properties properties = getProps(server);
       producer = new KafkaProducer<>(properties);

       logger.info("Producer init done");
   }

   public void put(String topic, String key, String value) throws ExecutionException, InterruptedException {
       logger.info("putting some stuff in");

       ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
       producer.send(record, (recMedia, e) -> {
           if(e != null){
               logger.info("Something fucked up");
               return;
           }

           logger.info(recMedia.topic());
           logger.info("" + recMedia.partition());
           logger.info("" + recMedia.offset());
           logger.info("" + recMedia.timestamp());
       }).get();
   }

   public void close(){
       producer.close();
   }

   private Properties getProps(String server){
       Properties properties = new Properties();

       properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
       properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

       return properties;
   }
}
