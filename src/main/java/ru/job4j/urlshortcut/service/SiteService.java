package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Site;

import java.util.List;

public interface SiteService {
    List registrate(Site site);
    Site login(Site site);
}
