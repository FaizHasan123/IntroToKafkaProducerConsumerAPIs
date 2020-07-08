import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

class producer_demo{

	public static void main(String args[]) throws InterruptedException {
		Properties producer_props = new Properties();
		producer_props.put("bootstrap.servers", "localhost:9092");
		producer_props.put("acks", "all");
		producer_props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer_props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(producer_props);
		 for (int i = 0; i < 1000; i++) {
		     producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
		     Thread.sleep(5000);
		 }

		 producer.close();
	}
}