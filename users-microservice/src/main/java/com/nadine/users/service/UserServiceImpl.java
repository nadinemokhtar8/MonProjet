package com.nadine.users.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nadine.users.entities.Role;
import com.nadine.users.entities.User;
import com.nadine.users.exception.EmailAlreadyExistsException;
import com.nadine.users.exception.ExpiredTokenException;
import com.nadine.users.exception.InvalidTokenException;
import com.nadine.users.register.RegistationRequest;
import com.nadine.users.repos.RoleRepository;
import com.nadine.users.repos.UserRepository;
import com.nadine.users.util.EmailSender;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    UserRepository userRep;

    @Autowired
    RoleRepository roleRep;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);
        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public User registerUser(RegistationRequest request) {
        Optional<User> optionaluser = userRep.findByEmail(request.getEmail());
        if (optionaluser.isPresent()) {
            throw new EmailAlreadyExistsException("email déjà existant!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setEnabled(false);

        Role r = roleRep.findByRole("USER");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);

        newUser.setVerificationCode(UUID.randomUUID().toString());
        newUser.setCodeCreationDate(new Date());
        userRep.save(newUser);

        try {
            emailSender.sendEmail(newUser.getEmail(),
                    "<p>Bonjour " + newUser.getUsername() + "</p><p>Votre code : "
                            + newUser.getVerificationCode() + "</p>");
        } catch (RuntimeException exception) {
            LOGGER.log(Level.WARNING,
                    "Verification email was not sent. Use this code manually for {0}: {1}",
                    new Object[]{newUser.getEmail(), newUser.getVerificationCode()});
        }
        return newUser;
    }

    @Override
    public User verifyEmail(String code) {
        Optional<User> optionalUser = userRep.findByVerificationCode(code);
        if (optionalUser.isEmpty()) {
            throw new InvalidTokenException("code invalide");
        }

        User user = optionalUser.get();
        long minutes = Duration.between(user.getCodeCreationDate().toInstant(), new Date().toInstant()).toMinutes();
        if (minutes > 30) {
            throw new ExpiredTokenException("code expiré");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        return userRep.save(user);
    }
}
