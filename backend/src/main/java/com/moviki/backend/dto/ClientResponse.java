package com.moviki.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClientResponse {

    private String name;
    private String email;
    private String profilePicturePath;
    private LocalDateTime createdAt;
}
