package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    Site findByLogin(String login);
    Site findBySite(String site);
    Site findById(int id);
}
