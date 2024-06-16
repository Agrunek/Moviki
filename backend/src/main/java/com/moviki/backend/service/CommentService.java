package com.moviki.backend.service;

import com.moviki.backend.model.Comment;
import com.moviki.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment getCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            log.info("Comment with id: {} doesn't exist!", id);
        }

        return optionalComment.orElse(null);
    }

    @Transactional
    public Comment saveComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment with id: {} saved successfully!", comment.getId());
        return savedComment;
    }

    @Transactional
    public Comment updateComment(Comment comment) {
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment with id: {} updated successfully!", comment.getId());
        return updatedComment;
    }

    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
