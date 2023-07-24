package com.evoke.example.util;

import com.evoke.example.entities.ERole;
import com.evoke.example.entities.Role;
import com.evoke.example.repository.RolesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RolesUtils {
    @Autowired
    RolesRespository rolesRespository;

    public void mapRoles(Set<String> userRoles, Set<Role> dbRoles) {

        if (userRoles.isEmpty() || null == userRoles) {
            addDbRole(ERole.ROLE_USER, dbRoles);
        } else {
            userRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN": {
                        addDbRole(ERole.ROLE_ADMIN, dbRoles);
                        break;
                    }
                    case "ROLE_HR": {
                        addDbRole(ERole.ROLE_HR, dbRoles);
                        break;
                    }
                    default: {
                        addDbRole(ERole.ROLE_USER, dbRoles);
                        break;
                    }

                }


            });
        }
    }

    private void addDbRole(ERole erole, Set<Role> dbRoles) {
        Role userRole = rolesRespository.findByName(erole)
                .orElseThrow(() -> new RuntimeException("Error: Role Not Found"));
        dbRoles.add(userRole);
    }
}
