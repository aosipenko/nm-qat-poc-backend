import java.util.concurrent.ExecutionException;

public class Runner {

    public static void main(String... args) throws ExecutionException, InterruptedException {
        String host = "localhost:9092";
        String topic = "topic";
        String group = "app";

        Consumer consumer = new Consumer(host, group, topic);
        consumer.run();

        Producer producer = new Producer(host);

        producer.put(topic, "user1", "Joe");
        producer.put(topic, "user2", "Doe");

        producer.close();

    }
}
