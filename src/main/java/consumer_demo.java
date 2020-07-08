import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.*;

class consumer_demo{

	public static void main(String args[]) throws InterruptedException {
		Properties consumer_props = new Properties();
		consumer_props.put("bootstrap.servers", "localhost:9092");
		consumer_props.put("acks", "all");
		consumer_props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		consumer_props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Consumer<String, String> consumer = new KafkaConsumer<>(consumer_props);
		while(true) {
			
		}

		
	}
}