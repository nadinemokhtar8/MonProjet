package com.nadine.users;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nadine.users.entities.Role;
import com.nadine.users.entities.User;
import com.nadine.users.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class UsersMicroserviceApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UsersMicroserviceApplication.class, args);
    }

    @PostConstruct
    void init_users() {
        if (userService.findUserByUsername("admin") == null) {
            userService.addRole(new Role(null, "ADMIN"));
            userService.addRole(new Role(null, "USER"));

            userService.saveUser(new User(null, "admin", "123", true, "admin@nadinebooks.com", null, null, new ArrayList<>()));
            userService.saveUser(new User(null, "nadine", "123", true, "nadine@nadinebooks.com", null, null, new ArrayList<>()));
            userService.saveUser(new User(null, "yassine", "123", true, "yassine@nadinebooks.com", null, null, new ArrayList<>()));

            userService.addRoleToUser("admin", "ADMIN");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("nadine", "USER");
            userService.addRoleToUser("yassine", "USER");
        }
    }
}
