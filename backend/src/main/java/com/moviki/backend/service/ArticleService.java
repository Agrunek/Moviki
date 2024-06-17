package com.moviki.backend.service;

import com.moviki.backend.dto.ArticleRequest;
import com.moviki.backend.model.Article;
import com.moviki.backend.model.Client;
import com.moviki.backend.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public Article getArticleByTitle(String title) {
        return articleRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Article not found!"));
    }

    @Transactional
    public Article createArticle(Client client, String title, ArticleRequest articleRequest) {
        Optional<Article> optionalArticle = articleRepository.findByTitle(title);

        if (optionalArticle.isPresent()) {
            throw new RuntimeException("Article already exists!");
        }

        Article article = new Article().withClient(client).withTitle(title).withContent(articleRequest.getContent());
        return articleRepository.save(article);
    }

    @Transactional
    public Article editArticle(Client client, String title, ArticleRequest articleRequest) {
        Optional<Article> optionalArticle = articleRepository.findByTitle(title);
        Article article = optionalArticle.orElseThrow(() -> new RuntimeException("Article not found!"));

        if (!article.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Client is not an author of a article!");
        }

        return articleRepository.save(article.withContent(articleRequest.getContent()));
    }

    @Transactional
    public void removeArticle(Client client, String title) {
        Optional<Article> optionalArticle = articleRepository.findByTitle(title);
        Article article = optionalArticle.orElseThrow(() -> new RuntimeException("Article not found!"));

        if (!article.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Client is not an author of a article!");
        }

        articleRepository.deleteById(article.getId());
    }
}
