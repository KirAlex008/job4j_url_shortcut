package ru.job4j.urlshortcut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private Coder coder;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public List convert(Url url, HttpServletRequest request) {
        Url urlRep = urlRepository.findByUrl(url.getUrl());
        List container = new ArrayList();
        Boolean flag = true;
        Map map = new HashMap<>();
        Object body = map;
        if (urlRep != null) {
            map.put("code", url.getShorturl());
            flag = false;
        } else {
            String shorturl = coder.genhash();
            Principal principal = request.getUserPrincipal();
            String siteName = principal.getName();
            Site site = siteRepository.findByLogin(siteName);
            urlRep = new Url(site, shorturl, url.getUrl());
            urlRepository.save(urlRep);
            map.put("code ", shorturl);
        }
        container.add(body);
        container.add(flag);
        return container;
    }

    @Override
    public List redirect(String code) {
        Url url = urlRepository.findByShorturl(code);
        HttpHeaders httpHeaders = null;
                List container = new ArrayList();
        Boolean flag = true;
        if (url == null) {
            flag = false;
        } else {
            urlRepository.updateCounter(url.getId());
            httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", url.getUrl());
        }
        container.add(httpHeaders);
        container.add(flag);
        return container;
    }

    @Override
    public Flux<Object> getStatistic() {
        List list = new ArrayList();
        for (Url url:urlRepository.findAll()) {
            Map map = new HashMap<>();
            map.put("url ", url.getUrl());
            map.put("total ", url.getCounter());
            list.add(map);
        };
        return Flux.fromIterable(list);
    }
}
