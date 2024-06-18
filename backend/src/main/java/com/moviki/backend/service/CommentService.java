package com.moviki.backend.service;

import com.moviki.backend.dto.CommentRequest;
import com.moviki.backend.model.Article;
import com.moviki.backend.model.Client;
import com.moviki.backend.model.Comment;
import com.moviki.backend.repository.ArticleRepository;
import com.moviki.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public List<Comment> getAllCommentsByArticleTitle(String title) {
        return commentRepository.findAllByArticleTitle(title);
    }

    @Transactional
    public Comment createComment(Client client, String articleTitle, CommentRequest commentRequest) {
        Optional<Article> optionalArticle = articleRepository.findByTitle(articleTitle);
        Article article = optionalArticle.orElseThrow(() -> new RuntimeException("Article not found!"));
        Comment comment = new Comment().withClient(client).withArticle(article).withContent(commentRequest.getContent());
        return commentRepository.save(comment);
    }
}
