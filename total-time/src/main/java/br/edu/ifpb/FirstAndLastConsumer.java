package br.edu.ifpb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class FirstAndLastConsumer {
    private final static String FIRST_LAST_QUEUE_NAME = "first-last";

    private static long firstMessageTimestamp;

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // we're not using try-with-resources because we want the process to stay alive
        // while the consumer (worker) is listening asynchronously for messages to arrive
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();


        // queue with only first and last messages
        channel.queueDeclare(FIRST_LAST_QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            if (message.split("-")[0].equals("0")) {
                firstMessageTimestamp = Long.parseLong(message.split("-")[1].trim());
                System.out.println(firstMessageTimestamp);
            } else if (message.split("-")[0].equals("999999")) {
                System.out.println(firstMessageTimestamp);
                System.out.println(Long.parseLong(message.split("-")[1].trim()) - firstMessageTimestamp);
            }
            // System.out.println(" [x] Received '" + message + "'");
            // System.out.println(" [x] Done");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        boolean autoAck = false;
        channel.basicConsume(FIRST_LAST_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

}
