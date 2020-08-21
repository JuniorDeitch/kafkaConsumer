package kafkaReader.kafka.service.impl;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractAvroKafkaConsumerService<T> extends AbstractAvroKafkaConsumer<T> {

    @Value("${KAFKA.SSL_AUTH}")
    private Boolean KAFKA_SSL_AUTH;

    @Value("${KAFKA.BOOTSTRAP_SERVERS}")
    private String KAFKA_BOOTSTRAP_SERVERS;

    @Value("${KAFKA.SCHEMA_REGISTRY_URL}")
    private String KAFKA_SCHEMA_REGISTRY_URL;

    @Value("${KAFKA.SECURITY_PROTOCOL}")
    private String KAFKA_SECURITY_PROTOCOL;

    @Value("${KAFKA.SASL_MECHANISM}")
    private String KAFKA_SASL_MECHANISM;

    @Value("${KAFKA.SASL_MECHANISM_JAAS_CONFIG_USER}")
    private String KAFKA_SASL_MECHANISM_JAAS_CONFIG_USER;

    @Value("${KAFKA.SASL_MECHANISM_JAAS_CONFIG_PWD}")
    private String KAFKA_SASL_MECHANISM_JAAS_CONFIG_PWD;

    @Value("${KAFKA.SSL_TRUSTSTORE_PWD}")
    private String KAFKA_SSL_TRUSTSTORE_PWD;

    @Value("${KAFKA.SSL_KEY_PWD}")
    private String KAFKA_SSL_KEY_PWD;

    @Value("${KAFKA.SSL_KEYSTORE_PWD}")
    private String KAFKA_SSL_KEYSTORE_PWD;

    @Value("${KAFKA.MAX_POLL_RECORDS}")
    private Integer KAFKA_MAX_POLL_RECORDS;

    @Value("${KAFKA.MAX_POLL_TIMEOUT}")
    private Long KAFKA_MAX_POLL_TIMEOUT;

    public AbstractAvroKafkaConsumerService(String topicName) {
        super(topicName);
    }

	protected void addConsumerConfig() {

        bootstrapServers = KAFKA_BOOTSTRAP_SERVERS;
        schemaRegistryUrl = KAFKA_SCHEMA_REGISTRY_URL;
        groupId = "tcc";
        clientId = "tcc_client";
        maxPollRecords = KAFKA_MAX_POLL_RECORDS;
        pollTimeout = KAFKA_MAX_POLL_TIMEOUT;

        sslAuth = KAFKA_SSL_AUTH;
        securityProtocol = KAFKA_SECURITY_PROTOCOL;
        saslMechanism = KAFKA_SASL_MECHANISM;
        saslMechanismJaasConfigUser = KAFKA_SASL_MECHANISM_JAAS_CONFIG_USER;
        saslMechanismJaasConfigPwd = KAFKA_SASL_MECHANISM_JAAS_CONFIG_PWD;
        sslTruststorePwd = KAFKA_SSL_TRUSTSTORE_PWD;
        sslKeyPwd = KAFKA_SSL_KEY_PWD;
        sslKeyStorePwd = KAFKA_SSL_KEYSTORE_PWD;

    }

}
