package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.urlshortcut.model.Url;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    Url findById(int id);
    Url findByShorturl(String shorturl);
    Url findByUrl(String url);
}
