import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

class ProducerDemo {

	public static void main(String args[]) throws InterruptedException {
		Properties producerProps = new Properties();
		producerProps.put("bootstrap.servers", "localhost:9092");
		producerProps.put("acks", "all");
		producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(producerProps);
		 for (int i = 0; i < 1000; i++) {
		     producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
		     Thread.sleep(5000);
		 }
		 producer.close();
	}
}