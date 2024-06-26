package com.moviki.backend.dto;

import com.moviki.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String name;
    private String profilePicturePath;
    private LocalDateTime createdAt;
    private List<Role> roles;
    private List<String> articles;
}
