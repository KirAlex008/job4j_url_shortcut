package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
    Url findById(int id);
    Url findByShorturl(String shorturl);
    Url findByUrl(String url);

    @Transactional
    @Modifying
    @Query("UPDATE Url u set u.counter = u.counter + 1 WHERE u.id = :id")
    int updateCounter( @Param("id") int id);

}
