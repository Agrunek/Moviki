package com.moviki.backend.controller;

import com.moviki.backend.dto.CommentRequest;
import com.moviki.backend.dto.CommentResponse;
import com.moviki.backend.model.Client;
import com.moviki.backend.model.Comment;
import com.moviki.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{title}")
    public ResponseEntity<List<CommentResponse>> getAllArticleComments(@PathVariable String title) {
        List<Comment> comments = commentService.getAllCommentsByArticleTitle(title);
        List<CommentResponse> commentResponses = comments.stream().map(comment -> new CommentResponse(comment.getId(), comment.getContent(), comment.getArticle().getTitle(), comment.getClient().getName(), comment.getCreatedAt())).toList();
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("/{title}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> createComment(@PathVariable String title, @RequestBody CommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = (Client) authentication.getPrincipal();
        Comment comment = commentService.createComment(currentClient, title, request);
        CommentResponse commentResponse = new CommentResponse(comment.getId(), comment.getContent(), comment.getArticle().getTitle(), comment.getClient().getName(), comment.getCreatedAt());
        return ResponseEntity.ok(commentResponse);
    }
}
