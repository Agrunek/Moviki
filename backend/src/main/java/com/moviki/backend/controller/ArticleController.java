package com.moviki.backend.controller;

import com.moviki.backend.dto.ArticleRequest;
import com.moviki.backend.dto.ArticleResponse;
import com.moviki.backend.model.Article;
import com.moviki.backend.model.Client;
import com.moviki.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{title}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String title) {
        Article article = articleService.getArticleByTitle(title);
        ArticleResponse articleResponse = new ArticleResponse(article.getTitle(), article.getContent(), article.getMainImagePath(), article.getClient().getName(), article.getUpdatedAt());
        return ResponseEntity.ok(articleResponse);
    }

    @PostMapping("/{title}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ResponseEntity<ArticleResponse> createArticle(@PathVariable String title, @RequestBody ArticleRequest articleRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = (Client) authentication.getPrincipal();
        Article article = articleService.createArticle(currentClient, title, articleRequest);
        ArticleResponse articleResponse = new ArticleResponse(article.getTitle(), article.getContent(), article.getMainImagePath(), article.getClient().getName(), article.getUpdatedAt());
        return ResponseEntity.ok(articleResponse);
    }

    @PutMapping("/{title}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable String title, @RequestBody ArticleRequest articleRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = (Client) authentication.getPrincipal();
        Article article = articleService.editArticle(currentClient, title, articleRequest);
        ArticleResponse articleResponse = new ArticleResponse(article.getTitle(), article.getContent(), article.getMainImagePath(), article.getClient().getName(), article.getUpdatedAt());
        return ResponseEntity.ok(articleResponse);
    }

    @DeleteMapping("/{title}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ResponseEntity<String> deleteArticle(@PathVariable String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = (Client) authentication.getPrincipal();
        articleService.removeArticle(currentClient, title);
        return ResponseEntity.ok("Article deleted successfully!");
    }
}
