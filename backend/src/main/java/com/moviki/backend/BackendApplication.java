package com.moviki.backend;

import com.moviki.backend.model.Role;
import com.moviki.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role().withName("CLIENT").withDescription("Base role with standard privileges."));
            roles.add(new Role().withName("EDITOR").withDescription("Has ability to modify content."));
            roles.add(new Role().withName("ADMIN").withDescription("Full access."));

            roles.forEach(role -> {
                Optional<Role> optionalRole = roleRepository.findByName(role.getName());
                if (optionalRole.isEmpty()) {
                    roleRepository.save(role);
                }
            });
        };
    }
}
