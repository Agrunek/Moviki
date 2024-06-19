package com.moviki.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private String articleTitle;
    private String clientName;
    private LocalDateTime createdAt;
}
