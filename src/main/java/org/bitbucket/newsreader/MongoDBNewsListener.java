package org.bitbucket.newsreader;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;

/**
 *
 * @author Geronimo
 */
public class MongoDBNewsListener implements NewsListener {

    @Override
    public boolean publish(NewsEntry e)
    {
        MongoCollection c = MongoDBFactory.getCollection();

        final Map<String,Object> document = new HashMap<>();
	document.put("link", e.getLink());
	document.put("title", e.getTitle());
	document.put("date", e.getDate());
        Document d = new Document(document);
        FindIterable f = c.find(d);
        if (!f.iterator().hasNext()) {
            c.insertOne(new Document(d));
            System.out.println("publish() -> MongoDB");
            return true;
        }
        return false;
    }

}
