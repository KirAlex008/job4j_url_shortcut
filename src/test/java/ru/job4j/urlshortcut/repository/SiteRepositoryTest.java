package ru.job4j.urlshortcut.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;

@DataJpaTest
@RunWith(SpringRunner.class)

class SiteRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void whenCreateUrl() {
        Site site = new Site("test@mail.ru", "123", "fff");
        Url url1 = new Url(site, "111", "222");
        urlRepository.save(url1);
        Url result = urlRepository.findById(url1.getId());
        Assert.assertEquals(url1, result);
    }

    @Test
    public void whenCreateSite() {
        Site site = new Site("test@mail.ru", "123", "fff");
        siteRepository.save(site);
        Site result = siteRepository.findById(site .getId());
        Assert.assertEquals(site , result);
    }

    @Test
    public void whenCreateSite2() {
        Site site = new Site("test@mail.ru", "123", "fff");
        siteRepository.save(site);
        Site result = siteRepository.findBySite(site.getSite());

        Assert.assertEquals(site , result);
    }

}
