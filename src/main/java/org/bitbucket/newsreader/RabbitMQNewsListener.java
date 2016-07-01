package org.bitbucket.newsreader;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Geronimo
 */
public class RabbitMQNewsListener implements NewsListener {

    private final static String QUEUE_NAME = "news";

    @Override
    public boolean publish(NewsEntry e)
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigReader.getInstance().getRabbitMQHost());
        factory.setUsername("user");
        factory.setPassword("1234");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message =  e.getTitle() + " " +  e.getSource() + " " +
                    e.getLink();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
}