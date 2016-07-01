package org.bitbucket.newsreader;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        RSSParser parser = new RSSParser();

        List<NewsListener> ml = NewsListenerFactory.createListeners();

        ml.stream().forEach((l) -> {
            parser.addListener(l);
        });

        parser.parse();
    }
}