import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

class consumerDemo {

	public static void main(String args[]) throws InterruptedException {
		Properties consumerProps = new Properties();
		 consumerProps.put("bootstrap.servers", "broker-5-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093,broker-2-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093,broker-4-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093,broker-1-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093,broker-0-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093,broker-3-rgtvthnhvdqfxgjw.kafka.svc06.us-south.eventstreams.cloud.ibm.com:9093");
		 consumerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"token\" password=\"oZ17lr1q6tH52OrnJ913uU_XGhSn0MAMpHj5nOEqgFaN\";");
		 consumerProps.put("security.protocol", "SASL_SSL");
		 consumerProps.put("sasl.mechanism", "PLAIN");
		 consumerProps.put("ssl.protocol", "TLSv1.2");
		 consumerProps.put("ssl.enabled.protocols", "TLSv1.2");
		 consumerProps.put("ssl.endpoint.identification.algorithm", "HTTPS");
		 consumerProps.put("group.id", "KafkaExampleConsumer");
		 consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		 consumerProps.put("group.id", "G1");
		 Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		 consumer.subscribe(Arrays.asList("getting-started"));
		 while (true) {
		    ConsumerRecords<String, String> records = consumer.poll(100);
		    for (ConsumerRecord<String, String> record : records)
		      System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		 }
	}
}