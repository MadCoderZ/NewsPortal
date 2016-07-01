package org.bitbucket.newsreader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Geronimo
 */
public class NewsListenerFactory {

    public static List<NewsListener> createListeners()
    {
        List<NewsListener> listeners = new ArrayList<>();

        listeners.add(new MongoDBNewsListener());
        listeners.add(new RabbitMQNewsListener());

        System.out.println("createdListeners()");

        return listeners;
    }
}