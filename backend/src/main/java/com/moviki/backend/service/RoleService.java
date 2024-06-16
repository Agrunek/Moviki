package com.moviki.backend.service;

import com.moviki.backend.model.Role;
import com.moviki.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRoleById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty()) {
            log.info("Role with id: {} doesn't exist!", id);
        }

        return optionalRole.orElse(null);
    }

    @Transactional
    public Role saveRole(Role role) {
        Role savedRole = roleRepository.save(role);
        log.info("Role with id: {} saved successfully!", role.getId());
        return savedRole;
    }

    @Transactional
    public Role updateRole(Role role) {
        Role updatedRole = roleRepository.save(role);
        log.info("Role with id: {} updated successfully!", role.getId());
        return updatedRole;
    }

    @Transactional
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
