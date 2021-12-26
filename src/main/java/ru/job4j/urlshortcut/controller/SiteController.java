package ru.job4j.urlshortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.service.Coder;
import ru.job4j.urlshortcut.service.SiteServiceImpl;
import ru.job4j.urlshortcut.service.UrlServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.List;

@RestController
public class SiteController {

    @Autowired
    private SiteServiceImpl siteService;

    @Autowired
    private UrlServiceImpl urlService;

    @PostMapping("/login")
    public Site loginSite(@RequestBody Site site) {
        return siteService.login(site);
    }

    @PostMapping("/convert")
    public ResponseEntity<Void> convert(@RequestBody Url url, HttpServletRequest request) {
        List container = urlService.convert(url, request);
        Object body = container.get(0);
        Boolean flag = (Boolean)container.get(1);
        return new ResponseEntity(
                body, flag == true ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> create(@RequestBody Site site) {
        List container = siteService.registrate(site);
        Object body = container.get(0);
        Boolean flag = (Boolean)container.get(1);
        return new ResponseEntity(
                body, flag == true ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        List container = urlService.redirect(code);
        HttpHeaders httpHeaders = (HttpHeaders)container.get(0);
        Boolean flag = (Boolean)container.get(1);
        return new ResponseEntity(
                httpHeaders, flag == true ? HttpStatus.MOVED_TEMPORARILY : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/statistic", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> statistic() {
        Flux<Object> data = urlService.getStatistic();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }
}


