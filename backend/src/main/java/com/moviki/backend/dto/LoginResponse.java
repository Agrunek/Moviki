package com.moviki.backend.dto;

import com.moviki.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long expiresIn;
    private List<Role> roles;
}
