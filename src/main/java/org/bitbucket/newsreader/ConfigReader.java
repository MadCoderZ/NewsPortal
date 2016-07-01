package org.bitbucket.newsreader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigReader {

    private static ConfigReader instance = null;

    private CompositeConfiguration config = null;

    private String mongoDBhost = null;
    private String mongoDBuser = null;
    private String mongoDBpass = null;
    private int mongoDBport;
    private String rabbitMQHost = null;

    private String[] rssUrls = null;

    protected ConfigReader()
    {
        this.config = new CompositeConfiguration();
        try {
            this.config.addConfiguration(new PropertiesConfiguration("newsreader.properties"));
        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Read property keys related to mongoDB config.
        this.mongoDBhost = config.getString("mongodb.host");
        this.mongoDBport = config.getInt("mongodb.port");
        this.mongoDBuser = config.getString("mongodb.user");
        this.mongoDBpass = config.getString("mongodb.passwd");
        this.rabbitMQHost = config.getString("rabbitmq.host");

        // RSS property keys.
        this.rssUrls = config.getStringArray("rss.url");
    }

    public String getRabbitMQHost()
    {
        return rabbitMQHost;
    }

    public String getMongoDBpass()
    {
        return this.mongoDBpass;
    }

    public int getMongoDBport()
    {
        return this.mongoDBport;
    }

    public static ConfigReader getInstance()
    {
        if (instance == null) instance = new ConfigReader();
        return instance;
    }

    public String[] getRssUrls() {
        return rssUrls;
    }

    public String getMongoDBhost()
    {
        return this.mongoDBhost;
    }

    public String getMongoDBuser()
    {
        return this.mongoDBuser;
    }

    public String getMongoDBpasswd()
    {
        return this.mongoDBpass;
    }
}