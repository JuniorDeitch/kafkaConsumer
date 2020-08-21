package kafkaReader.kafka.service.impl;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

public abstract class AbstractAvroKafkaConsumer<T> extends AbstractKafkaConsumer<T> {

    protected String schemaRegistryUrl;

    public AbstractAvroKafkaConsumer(String topicName, String bootstrapServers, String schemaRegistryUrl) {
        super(topicName, bootstrapServers);
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public AbstractAvroKafkaConsumer(String topicName) {
        super(topicName);
    }

    @Override
    protected void consumerConfig() {
        putProperty(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        putProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        putProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
    }

}
