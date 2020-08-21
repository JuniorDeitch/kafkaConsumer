package kafkaReader.kafka.service.impl;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKafkaCommunication<T> {

    protected String bootstrapServers;
    protected String topicName;
    protected String authLoginConfig;
    private Map<String, Object> props = new HashMap<String, Object>();

    protected boolean sslAuth;
    protected String securityProtocol;
    protected String saslMechanism;
    protected String saslMechanismJaasConfigUser;
    protected String saslMechanismJaasConfigPwd;
    protected String sslTrustStoreLocation;
    protected String sslKeyStoreLocation;
    protected String sslTruststorePwd;
    protected String sslKeyPwd;
    protected String sslKeyStorePwd;


    public AbstractKafkaCommunication(String topicName) {
        super();
        this.topicName = topicName;
    }


    protected void addConfigProps(final String key, final Object value) {
        props.put(key, value);
    }

    protected Map<String, Object> getProps() {
        return props;
    }

    protected Object getProperty(String key) {
        return props.get(key);
    }

    protected void sslConsumerConfig() {

        if (sslAuth) {

            props.put("security.protocol", securityProtocol);

            props.put("sasl.mechanism", saslMechanism);

            props.put("sasl.mechanism.jaas.config",
                    "org.apache.kafka.common.security.plain.PlainLoginModule required username=\""
                            + saslMechanismJaasConfigUser + "\" password=\"" + saslMechanismJaasConfigPwd + " + \";");

            props.put("ssl.truststore.location", sslTrustStoreLocation);

            props.put("ssl.keystore.location", sslKeyStoreLocation);

            props.put("ssl.truststore.password", sslTruststorePwd);

            props.put("ssl.key.password", sslKeyPwd);

            props.put("ssl.keystore.password", sslKeyStorePwd);
        }

    }

}

