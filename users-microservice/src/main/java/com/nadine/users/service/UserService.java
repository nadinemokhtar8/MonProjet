package com.nadine.users.service;

import com.nadine.users.entities.Role;
import com.nadine.users.entities.User;
import com.nadine.users.register.RegistationRequest;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    User registerUser(RegistationRequest request);
    User verifyEmail(String code);
}
