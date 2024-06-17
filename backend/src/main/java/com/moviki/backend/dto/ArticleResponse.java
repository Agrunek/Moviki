package com.moviki.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ArticleResponse {

    private String title;
    private String content;
    private String mainImagePath;
    private String client;
    private LocalDateTime updatedAt;
}
