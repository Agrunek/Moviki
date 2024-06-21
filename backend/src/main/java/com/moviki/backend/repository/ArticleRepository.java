package com.moviki.backend.repository;

import com.moviki.backend.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    @Query("FROM Article WHERE client.id = :id")
    List<Article> findAllByClientId(Long id);
}
