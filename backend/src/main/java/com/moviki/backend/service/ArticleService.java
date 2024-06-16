package com.moviki.backend.service;

import com.moviki.backend.model.Article;
import com.moviki.backend.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public Article getArticleById(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isEmpty()) {
            log.info("Article with id: {} doesn't exist!", id);
        }

        return optionalArticle.orElse(null);
    }

    @Transactional
    public Article saveArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        log.info("Article with id: {} saved successfully!", article.getId());
        return savedArticle;
    }

    @Transactional
    public Article updateArticle(Article article) {
        Article updatedArticle = articleRepository.save(article);
        log.info("Article with id: {} updated successfully!", article.getId());
        return updatedArticle;
    }

    @Transactional
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }
}
