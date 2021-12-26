package ru.job4j.urlshortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Site login(Site site) {
        return siteRepository.findByLogin(site.getLogin());
    }

    @Override
    public List registrate(Site site) {
        Site siteExist = siteRepository.findBySite(site.getSite());
        List container = new ArrayList();
        Boolean flag = false;
        Map map = new HashMap<>();
        Object body = map;
        if (siteExist != null) {
            map.put("registration ", false);
            map.put("login", " ");
            map.put("password", " ");
        } else {
            String pass = RandomStringUtils.random(6, true, true);
            String log = RandomStringUtils.randomAlphabetic(6);
            site.setPassword(encoder.encode(pass));
            site.setLogin(log);
            this.siteRepository.save(site);
            map.put("registration ", true);
            map.put("login", log);
            map.put("password", pass);
            flag = true;
        }
        container.add(body);
        container.add(flag);
        return container;
    }
}
