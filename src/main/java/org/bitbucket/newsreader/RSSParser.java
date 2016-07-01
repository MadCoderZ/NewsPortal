package org.bitbucket.newsreader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Geronimo
 */
public class RSSParser {

    private ConfigReader configReader;
    private final List<NewsListener> listeners;
    private List<URL> feeds;

    public RSSParser()
    {
        this.listeners = new ArrayList<>();
        this.feeds = new ArrayList<>();
        this.configReader = new ConfigReader();

        for (String url : this.configReader.getRssUrls()) {
            try {
                this.addFeed(url);
            } catch (MalformedURLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private List<NewsEntry> getNewsEntries()
            throws IllegalArgumentException, FeedException, IOException
    {
        List<SyndEntry> newEntries = new ArrayList<>();
        for (URL myFeed : this.feeds) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(myFeed));

            newEntries.addAll(feed.getEntries());
        }

        List<NewsEntry> ne = new ArrayList<>();
        for (SyndEntry entry : newEntries) {
            NewsEntry e = new NewsEntry();
            e.setLink(entry.getLink());
            e.setTitle(entry.getTitle());
            Date d = entry.getPublishedDate() != null ?
                            entry.getPublishedDate() : entry.getUpdatedDate();
            e.setDate(d);

            ne.add(e);
        }
        return ne;
    }

    private Set<String> getLinks(List<NewsEntry> entries)
    {
        Set<String> links = new HashSet<>();
        entries.stream().forEach((e) -> {
            links.add(e.getLink());
        });
        return links;
    }

    private NewsEntry findNewsEntryByLink(String myLink, List<NewsEntry> myEntries)
    {
        NewsEntry r = null;
        for (NewsEntry e : myEntries) {
            if (myLink.compareTo(e.getLink()) == 0) {
                r = e;
                break;
            }
        }
        return r;
    }

    private void addFeed(String url) throws MalformedURLException
    {
        this.feeds.add(new URL(url));
        System.out.println("Added feed:" + url);
    }

    public void addListener(NewsListener l)
    {
        this.listeners.add(l);
    }

    public void parse()
    {
        List<NewsEntry> myEntries = new ArrayList<>();
        try {
            myEntries = this.getNewsEntries();
        } catch (IllegalArgumentException | FeedException | IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        Set<String> newLinks = this.getLinks(myEntries);
        for (String nl : newLinks) {
            NewsEntry e = this.findNewsEntryByLink(nl, myEntries);
            if (e == null) continue;
            for (NewsListener l : this.listeners) {
                if (!l.publish(e)) break;
            }
        }
    }
}