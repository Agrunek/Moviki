package com.moviki.backend.repository;

import com.moviki.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("FROM Comment WHERE article.title = :title")
    List<Comment> findAllByArticleTitle(String title);
}
