package com.moviki.backend.controller;

import com.moviki.backend.model.Article;
import com.moviki.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticlesById(@PathVariable Long id) {
        return ResponseEntity.ok().body(articleService.getArticleById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.saveArticle(article));
    }

    @PutMapping("/")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.updateArticle(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.ok().body("Deleted article successfully!");
    }
}
