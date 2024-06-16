package com.moviki.backend.controller;

import com.moviki.backend.model.Comment;
import com.moviki.backend.service.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(commentService.getCommentById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        return ResponseEntity.ok().body(commentService.saveComment(comment));
    }

    @PutMapping("/")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        return ResponseEntity.ok().body(commentService.updateComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().body("Deleted comment successfully!");
    }
}
