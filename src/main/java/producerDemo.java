import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

class ProducerDemo {

	public static void main(String args[]) throws InterruptedException {
		Properties producerProps = new Properties();
		producerProps.put("bootstrap.servers", "broker-5-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093, "
				                             + "broker-2-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093, "
				                             + "broker-4-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093, "
				                             + "broker-1-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093, "
				                             + "broker-0-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093, "
				                             + "broker-3-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093");
		producerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"token\" password=\"oZ17lr1q6tH52OrnJ913uU_XGhSn0MAMpHj5nOEqgFaN\";");
		producerProps.put("security.protocol", "SASL_SSL");
		producerProps.put("sasl.mechanism", "PLAIN");
		producerProps.put("ssl.protocol", "TLSv1.2");
		producerProps.put("ssl.enabled.protocols", "TLSv1.2");
		producerProps.put("ssl.endpoint.identification.algorithm", "HTTPS");
		producerProps.put("acks", "all");
		producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(producerProps);
		 for (int i = 0; i < 1000; i++) {
		     producer.send(new ProducerRecord<String, String>("getting-started", Integer.toString(i), Integer.toString(i)));
		     Thread.sleep(5000);
		 }
		 producer.close();
	}
}