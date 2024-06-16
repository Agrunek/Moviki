package com.moviki.backend.controller;

import com.moviki.backend.model.Role;
import com.moviki.backend.service.RoleService;
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
@RequestMapping("/role")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(roleService.getRoleById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.saveRole(role));
    }

    @PutMapping("/")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.updateRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok().body("Deleted role successfully!");
    }
}
