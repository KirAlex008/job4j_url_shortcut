package ru.job4j.urlshortcut.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Table(name = "site")
@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  /*  @OneToMany(mappedBy = "site")
    private List<Url> sites = new ArrayList<>();*/
    private String site;
    private String login;
    private String password;

    public Site() {
    }

   /* public Site(String login, String password) {
        this.login = login;
        this.password = password;
    }
*/
    public Site(String site, String login, String password) {
        this.site = site;
        this.login = login;
        this.password = password;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id == site.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
