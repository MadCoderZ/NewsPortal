package org.bitbucket.newsreader;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Geronimo
 */
public class MongoDBFactory {

    private static MongoClient mongoClient = null;

    protected MongoDBFactory()
    {
    }

    private static void initClient()
    {
        if (mongoClient == null) {
            //mongoClient = new MongoClient(new MongoClientURI("mongodb://" +
            //    ConfigReader.getInstance().getMongoDBuser() + ":" +
            //    ConfigReader.getInstance().getMongoDBpass() + "@" +
            //    ConfigReader.getInstance().getMongoDBhost() + ":" +
            //    ConfigReader.getInstance().getMongoDBport()
            //));
            mongoClient = new MongoClient(ConfigReader.getInstance().getMongoDBhost(),
                                ConfigReader.getInstance().getMongoDBport());
        }
        System.out.println("initClient()");
    }

    public static MongoCollection<Document> getCollection()
    {
        initClient();

	MongoDatabase db = mongoClient.getDatabase("newsreader");
        return db.getCollection("news");
    }
}