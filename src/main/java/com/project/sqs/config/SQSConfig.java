package com.project.sqs.config;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * Created by IntelliJ IDEA.
 * User: kimdongho
 * Date: 2023/02/09
 * DESC :
 */
@Configuration
public class SQSConfig {

    @Value("${application.properties.accesskey}")
    private String accessKey;

    @Value("${application.properties.secretKey}")
    private String secretKey;

    @Bean
    public SQSConnection amazonSQSAws() throws JMSException {

        ProviderConfiguration configuration = new ProviderConfiguration();

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))
                .build();

        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(configuration, sqsClient);

        return connectionFactory.createConnection();
    }
}
