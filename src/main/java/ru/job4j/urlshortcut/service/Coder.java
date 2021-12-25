package ru.job4j.urlshortcut.service;

import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;

@Service
public class Coder {

    public String genlogin() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    public String genpass() {
        return RandomStringUtils.random(6, true, true);
    }

    public String genhash() {
        return RandomStringUtils.random(7, true, true);
    }
}
