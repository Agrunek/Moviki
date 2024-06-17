package com.moviki.backend.dto;

import com.moviki.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private String name;
    private String email;
    private List<Role> roles;
}
