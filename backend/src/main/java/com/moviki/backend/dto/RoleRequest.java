package com.moviki.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleRequest {

    private String name;
    private String role;
}
