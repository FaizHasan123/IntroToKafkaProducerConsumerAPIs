import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.*;

class consumerDemo {

	public static void main(String args[]) throws InterruptedException {
		Properties consumerProps = new Properties();
		consumerProps.put("bootstrap.servers", "localhost:9092");

		consumerProps.put("group.id", "KafkaExampleConsumer");

		consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		consumer.subscribe(Collections.singletonList("test"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(1000);
			records.forEach(record -> {
				System.out.println(record.value());
			});
		}
	}
}