package br.edu.ifpb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

/**
 * This class represents a producer that sends messages to a queue
 */
public class NewTask {

    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()
        ) {
            boolean durable = true;
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

            String message;
            for (int i = 0; i < 1_000_000; i++) {
                message = i + "-" + System.currentTimeMillis();
                channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8));
                // System.out.println(" [x] Sent '" + message + "'");
            }

//            channel.basicPublish("", TASK_QUEUE_NAME,
//                MessageProperties.TEXT_PLAIN,
//                message.getBytes(StandardCharsets.UTF_8));
//            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
