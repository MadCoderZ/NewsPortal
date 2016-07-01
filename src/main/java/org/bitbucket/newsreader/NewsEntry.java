package org.bitbucket.newsreader;

import java.util.Date;

/**
 *
 * @author Geronimo
 */
public class NewsEntry {

    private String link;
    private String title;
    private Date date;

    public String getLink() {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getSource()
    {
        return this.getLink().replaceFirst(".*https?://([\\w.-]+)/.*", "<$1>");
    }
}