package br.edu.ifpb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * This class represents a worker that consumes messages from a queue
 */
public class Worker {

    private final static String TASK_QUEUE_NAME = "task_queue";
    private final static String FIRST_LAST_QUEUE_NAME = "first-last";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // we're not using try-with-resources because we want the process to stay alive
        // while the consumer (worker) is listening asynchronously for messages to arrive
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // queue with only first and last messages
        channel.queueDeclare(FIRST_LAST_QUEUE_NAME, false, false, false, null);

        // int prefetchCount = 1;
        // channel.basicQos(prefetchCount); // accept only one unack-ed message at a time (see below)

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            // System.out.println(" [x] Received '" + message + "'");

            long receivedTimestamp = Long.parseLong(message.split("-")[1].trim());
            int index = Integer.parseInt(message.split("-")[0].trim());

            System.out.println(index + " - " + (System.currentTimeMillis() - receivedTimestamp));

            // put only first and last messages in a new queue
            if (index == 0 || index == 999_999) {
                channel.basicPublish("", FIRST_LAST_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            }
            // System.out.println(" [x] Done");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

}
