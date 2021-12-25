package ru.job4j.urlshortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.service.Coder;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SiteController {

    @Autowired
    private Coder coder;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public Site loginSite(@RequestBody Site site) {
        return siteRepository.findByLogin(site.getLogin());
    }

    @PostMapping("/convert")
    public ResponseEntity<Void> convert(@RequestBody Url url, HttpServletRequest request) {
        Url urlRep = urlRepository.findByUrl(url.getUrl());
        Map map = new HashMap<>();
        Object body = map;
        if (urlRep != null) {
            map.put("code", url.getShorturl());
            return new ResponseEntity(
                    body,
                    HttpStatus.CONFLICT
            );
        }
        String shorturl = coder.genhash();
        Principal principal = request.getUserPrincipal();
        String siteName = principal.getName();
        Site site = siteRepository.findByLogin(siteName);
        urlRep = new Url(site, shorturl, url.getUrl());
        urlRepository.save(urlRep);
        map.put("code ", shorturl);
        return new ResponseEntity(
                body,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> create(@RequestBody Site site) {
        Site siteExist = siteRepository.findBySite(site.getSite());
        Map map = new HashMap<>();
        Object body = map;
        if (siteExist != null) {
            map.put("registration ", false);
            map.put("login", " ");
            map.put("password", " ");
            return new ResponseEntity(
                    body,
                    HttpStatus.CONFLICT
            );
        } else {
            String pass = coder.genpass();
            String log = coder.genlogin();
            site.setPassword(encoder.encode(pass));
            site.setLogin(log);
            this.siteRepository.save(site);
            map.put("registration ", true);
            map.put("login", log);
            map.put("password", pass);
            return new ResponseEntity(
                    body,
                    HttpStatus.CREATED
            );
        }
    }
    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        Url url = urlRepository.findByShorturl(code);
        if (url == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
        url.setCounter(url.getCounter() + 1);
        urlRepository.save(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", url.getUrl());
        return new ResponseEntity<>(
                httpHeaders, HttpStatus.MOVED_TEMPORARILY
        );
    }
    @GetMapping("/statistic")
    public ResponseEntity<Void> statistic() {
        Map map = new HashMap<>();
        for (Url url:urlRepository.findAll()) {
            map.put("url ", url.getUrl());
            map.put("total ", url.getCounter());
            };
        Object body = map;
        return new ResponseEntity(
                body,
                HttpStatus.OK
        );
    }
}


