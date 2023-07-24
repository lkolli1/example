package com.evoke.example.runner;

import com.evoke.example.entities.ERole;
import com.evoke.example.entities.Role;
import com.evoke.example.repository.RolesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RolesRunner implements CommandLineRunner {
    @Autowired
    RolesRespository rolesRespository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<ERole> eRoleList = Arrays.asList(ERole.ROLE_USER, ERole.ROLE_HR, ERole.ROLE_ADMIN);
        List<Role> roleList = new ArrayList<>();

        eRoleList.stream().forEach(erole -> {
            if (!rolesRespository.findByName(erole).isPresent()) {
                roleList.add(new Role(erole));
            }
        });
        rolesRespository.saveAll(roleList);
    }
}
