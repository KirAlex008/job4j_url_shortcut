package ru.job4j.urlshortcut.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "url")
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;
    private String shorturl;
    private String url;
    private int counter = 0;

    public Url() {
    }

    public Url(Site site, String shorturl, String url) {
        this.site = site;
        this.shorturl = shorturl;
        this.url = url;
    }
   /* public void addSite(Site site) {
        this.users.add(u);
    }*/

    /*public static Url of(Site site, String shortUrl, String url) {
        Url url1 = new Url();
        url1.shorturl = shortUrl;
        url1.url = url;
        url1.site = site;
        return url1;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shortUrl) {
        this.shorturl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String longUrl) {
        this.url = longUrl;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return id == url.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
