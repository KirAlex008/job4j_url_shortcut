package ru.job4j.urlshortcut.service;

import reactor.core.publisher.Flux;
import ru.job4j.urlshortcut.model.Url;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UrlService {
    List convert(Url url, HttpServletRequest request);
    List redirect(String code);
    Flux<Object> getStatistic();
}
