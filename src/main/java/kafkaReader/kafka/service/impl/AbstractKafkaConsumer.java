package kafkaReader.kafka.service.impl;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public abstract class AbstractKafkaConsumer<T> extends AbstractKafkaCommunication<T> {

    protected String groupId;
    protected String clientId;
    protected Integer maxPollRecords;
    protected String topicName;
    protected Long pollTimeout;

    private final Integer sessionTimeout = 30000;
    private final Integer heartbeatInterval = 10000;

    private KafkaConsumer<String, T> consumer;
    private ConsumerRecords<String, T> consumerRecords;

    private List<T> consumedMessages;

    public AbstractKafkaConsumer(String topicName, String bootstrapServers) {
        super(topicName);
        this.topicName = topicName;
        this.bootstrapServers = bootstrapServers;
    }

    public AbstractKafkaConsumer(String topicName) {
        super(topicName);
        this.topicName = topicName;
    }


    public List<T> consumeMessages() {
        consumedMessages = new ArrayList<>();

        System.out.println("");
        System.out.println("Begin read of topic " + topicName + ": " + new Date());
        System.out.println("");

        openChannel();

        System.out.println("");
        System.out.println("End reading of topic " + topicName + ": " + new Date());
        System.out.println("");

        return getConsumedMessages();
    }

    protected abstract void postFetchMessages();

    private void closeChannel() {
        consumer.close();
    }

    protected List<T> getConsumedMessages() {
        return consumedMessages;
    }

    private void openChannel() {
        buildConsumerConfig();
        buildConsumer();
        consume();
        closeChannel();
    }

    private void consume() {

        try {
            consumerRecords = consumer.poll(pollTimeout);

            while (!consumerRecords.isEmpty()) {

                Iterator<ConsumerRecord<String, T>> it = consumerRecords.records(topicName).iterator();

                while (it.hasNext()) {
                    consumedMessages.add(it.next().value());
                }

                postFetchMessages();

                consumer.commitSync();

                consumerRecords = consumer.poll(pollTimeout);
            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {

            closeChannel();
        }
    }

    protected void buildConsumer() {
        try {
            consumer = new KafkaConsumer<>(getProps());
            consumer.subscribe(Collections.singletonList(topicName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            throw e;
        }
    }

    private void buildConsumerConfig() {
        consumerConfigCore();
    }

    protected abstract void consumerConfig();

    protected void consumerConfigCore() {

        addConfigProps(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        addConfigProps(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        addConfigProps(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        addConfigProps(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        addConfigProps(ConsumerConfig.CLIENT_ID_CONFIG, clientId);

        addConfigProps(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        addConfigProps(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);

        addConfigProps(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        addConfigProps(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        System.setProperty("java.security.auth.login.config", authLoginConfig );

        addConfigProps(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);

        consumerConfig();

    }

    protected void putProperty(String key, Object value) {
        this.addConfigProps(key, value);
    }


}
