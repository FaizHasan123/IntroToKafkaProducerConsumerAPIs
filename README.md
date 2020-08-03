## Introduction
Apache Kafka is an event streaming platform that aids in the implementation of an event driven architecture. Rather than the point-to-point communication of REST API's, Kafka's model is one of applications producing messages (events) to a pipeline that can then be consumed by consumers. Producers are unaware of who is consuming their data and how. Similarly, consumers can consume messages at any point from the queue and are not tied to producers. This leads to the decoupling between producers and consumers that event driven architecture relies on. Check out [this page](https://kafka.apache.org/) for more information about Apache Kafka.

The Kafka [quickstart](https://kafka.apache.org/quickstart) provided on the Kafka webiste does an excellent job of explaining how the different components of Kafka work by interacting with it manually by running shell scripts in the command line. In this tutorial, I want to give an overview of how to interact with Kafka programatically.

Kafka provides 2 handy API's to communicate with your cluster though your code. The [producer API](https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) can produce events and the [consumer API](https://kafka.apache.org/25/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html) can consume events.

## Learning objectives
The objective of this tutorial is to demonstrate how to write Java programs to produce/consume messages to/from Apache Kafka. Since creating and maintaining a Kafka cluster can require lots of investment in terms of time and computational power, we also demonstrate Event Streams, a managed Kafka offering on IBM Cloud. 
After completing this tutorial, you will understand:
* What Apache Kafka is
* How to produce messages to Kafka programmatically 
* How to consumer messages from Kafka programmatically
* What IBM Event Streams is
* How to set up a Kafka cluster using IBM Event Streams

## Prerequistes
* [Apache Kafka](https://kafka.apache.org/downloads)
* [A pay-as-you-go IBM Cloud Account](https://cloud.ibm.com/login)

## Estimated Time
It will take approximately 40 minutes to go through this tutorial.

## Kafka concepts
### Topic ### 
### Partition ###
### Producer ###
### Consumer ###

## Introducing IBM Event Streams
While it is easy to get Kafka running on your machine for experimentation, managing a Kafka cluster with multiple servers in production can be quite cumbersome. IBM Event Streams is a managed Kafka service on IBM Cloud that allows developers to create Kafka clusters without having to worry about provisioning and maintaining a Kafka cluster.

Check out this video to learn more about Event Streams:

https://www.youtube.com/watch?v=XyNy7TcfJOc


## Understanding the Kafka producer API
The flow of the Kafka producer looks something like this [2]
![](images/Producer_Flow.png)

As we will see later on, our program first creates an object of type ProducerRecord. This object is fed to a serializer that is responsible for converting the message to a byte array. All messages stored in a Kafka topic have to be serialized to byte arrays first. Serialization is what helps Kafka scale to 100's of applications writing different kinds of data to a single topic. By serializing the data coming into Kafka, we no longer have to worry about our data conforming to a specific schema (JSON, XML, etc.) and only have to worry about this single "generic" data type being written [1].

After serializing our data, Kafka has to decide which partition to store it in. This is where the Partitioner comes in. If we explicitly specify which partition we would like our data to be stored in, the partitioner does not have to do anything and simply return our specified partition. If, however, we would instead like Kafka to select the partition for us, that is where the Partitioner comes into play. It selects the partition based on the key of the message it receives. Records are batched together before being sent to their respective topic and partition [3].

Finally, a separate thread is responsible for writing the message. If written successfully, the broker returns a RecordMetadata object. This RecordMetadata object contains the topic, the partition, and the offset within the partition at which the record is stored. If the write operation fails, the broker returns an error. Upon receiving an error, the producer may try to write the message again.

## Adding the Kafka Producer API to a Java Project

Follow [this](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients) link to get the latest version of Kafka to add to your maven project.

## Using the Kafka Producer API

To start with, here's a the code we will be using. The entire project (including the pom.xml file) is downloadable on [GitHub](https://github.com/FaizHasan123/IntroToKafkaProducerConsumerAPIs).

```java
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
```

Let's go through the code line by line starting from line 6.

We first create a Properties object (producer_props) where we will store the properties of the producer object.

Once we've stored our properties, we can create a producer object. To do so, we simply pass the properties we set in line 6–10 to a Producer constructor like so:

```java
Producer<String, String> producer = new KafkaProducer<>(producerProps);
```

We can now use our producer object to send messages toKafka using the producer.send method.

To do so, we will iterate over a for loop 100 times. In each iteration, we will send a message to Kafka and pause for 5 seconds.

```java
for (int i = 0; i < 1000; i++) {
	producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
	Thread.sleep(5000);
}
```

The producer's send method takes an object of type ProducerRecord. The ProducerRecord constructor we will be using is as follows

```java
ProducerRecord(String topic, K key, V value)
```

This creates a record with the topic as well as a key-value pair that we want to write. Other constructors exist (for example a constructor that includes a timestamp). To create our ProducerRecord, we will pass the topic "test" (if this topic does not already exist, Kafka will create it for us) and the key and value both equal to the iteration of the for loop we are in.

Running this code should start writing to Kafka. Follow the steps in the quickstart to learn how to start a Kafka server. You may get an error that looks like this:

![](images/Error.png)

But you can ignore it for now

We now have a producer that's writing messages to a Kafka topic. Now let's create a producer that can read those messages.

## Understanding the Kafka Consumer API

Now that we have an application that produces messages to a Kafka topic, we can write an application to consume those same messages. Before going ahead with an implementation of a Kafka consumer in Java, let's take a look at the concept of a consumer group.

We will revisit this concept when writing our Java code.


## Using the Kafka Consumer API

Just like the Producer code, the code for the consumer application can be found on [Github](https://github.com/FaizHasan123/IntroToKafkaProducerConsumerAPIs).

```java
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
```
We can see that constructing a consumer is very similar to constructing a producer. We first add our properties to a Java properties object and then apply those to a consumer object. Let's go through the code line-by-line:
* In line 14 of the code snippet, we specify the address of the Kafka broker we would like to read from. Since we only have 1 server running locally, this is specified as "localhost:9092"

```java
	consumerProps.put("bootstrap.servers", "localhost:9092");
```
* As mentioned earlier a consumer group is a group of consumers reading messages from the same Kafka topic. When creating a consumer, we need to specify the consumer group it belongs to. We will specify the group as KafkaExampleConsumer. This is done in line 16

```java
	consumerProps.put("group.id", "KafkaExampleConsumer");
```

* The consumer has to deserialize the the keys and values of messages serialized by the producer. This is taken care of in lines 18 and 20
```java
consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
```

* Finally, in line 22, we use the properties we just specified to create a Kafka consumer
```java
KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
```
* In line 23, we specify the topic we would like to read messages from. This is the topic "test" which we create earlier and are producing messages to.
```java
consumer.subscribe(Collections.singletonList("test"));
```
* The consumer polls the topic every 1000 milliseconds and prints the record value.
```java
ConsumerRecords<String, String> records = consumer.poll(1000);
			records.forEach(record -> {
				System.out.println(record.value());
			});
```

In this tutorial, we cover the simplest case of a Kafka implementation with a single producer and a single consumer writing messages to and reading messages from a single topic. Of course, this only scratches the surface of what Kafka is capable of. In a production environment you may have multiple Kafka brokers, producers, and consumer groups. This is what makes Kafka a powerful technology for implementing an Event Driven Architecture. However, at the same time, all of these different components of a cluster can make it difficult to manage. As a result, many users opt to use a managed Kafka service instead of managing a cluster themselves. An example of such a managed service is IBM Event Streams.


---
* [1] https://stackoverflow.com/questions/56207118/why-does-kafka-wants-data-to-be-binary-array-of-bytess-in-topics
* [2] Image Source: https://dzone.com/articles/take-a-deep-dive-into-kafka-producer-api
* [3] https://dzone.com/articles/take-a-deep-dive-into-kafka-producer-api
* [4] image source: https://kafka.apache.org/documentation/#intro_consumers
