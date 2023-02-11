package com.project.sqs.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.stereotype.Service;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.project.sqs.listener.SQSListener;

/**
 * Created by IntelliJ IDEA.
 * User: kimdongho
 * Date: 2023/02/09
 * DESC :
 */
@Service
public class SQSService {

    SQSConnection sqsConnection;
    final String QUEUE = "";

    SQSService(SQSConnection sqsConnection) {
        this.sqsConnection = sqsConnection;
    }

    public void sendMsg(String msg) {
        AmazonSQSMessagingClientWrapper client = this.sqsConnection.getWrappedAmazonSQSClient();

        try {
            if (client.queueExists(QUEUE)) {
                Session session = this.sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Queue queue = session.createQueue(QUEUE);

                MessageProducer producer = session.createProducer(queue);

                // Create the text message
                TextMessage message = session.createTextMessage(msg);
                // Set the message group ID
                message.setStringProperty("JMSXGroupID", "Default");
                // Send the message

                producer.send(message);

                System.out.println("Send MSG : " + msg);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveMsg() {

        try {
            Session session = this.sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(QUEUE);
            // Create a consumer for the 'MyQueue'
            MessageConsumer consumer = session.createConsumer(queue);

            // Start receiving incoming messages
            this.sqsConnection.start();

            Message receivedMessage = consumer.receive();

            // Cast the received message as TextMessage and display the text
            if (receivedMessage != null) {
                System.out.println("Received: " + ((TextMessage) receivedMessage).getText());
            } else {
                System.out.println("NOT RECEIVE MESSAGE");
            }

            this.sqsConnection.close();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void asyncReceiveMsg() {
        System.out.println("Async Receive Start");
        try {
            Session session = this.sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE);
            // Create a consumer for the 'MyQueue'.
            MessageConsumer consumer = session.createConsumer(queue);

            // Instantiate and set the message listener for the consumer.
            consumer.setMessageListener(new SQSListener());

            // Start receiving incoming messages.
            this.sqsConnection.start();

            // 10초
            Thread.sleep(10000);

        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // ETC

    public void sendMsgLog() {

    }

}