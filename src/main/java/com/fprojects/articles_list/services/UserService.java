package com.fprojects.articles_list.services;

import com.fprojects.articles_list.entities.RoleEntity;
import com.fprojects.articles_list.entities.UserEntity;
import com.fprojects.articles_list.exceptions.ResourceNotFoundException;
import com.fprojects.articles_list.repositories.RoleRepository;
import com.fprojects.articles_list.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    /**
     * Get all users
     */
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Get one user by email
     * @param email
     */
    public UserEntity getUserByEmail(String email) throws ResourceNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("Cant find user by email");
            throw new ResourceNotFoundException();
        }

        return user;
    }

    /**
     * Return user by username
     * @param username
     */
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("Cant find user by username");
            throw new UsernameNotFoundException("Cant find user by username: " + username);
        }

        return user;
    }

    /**
     * Register user with role ROLE_USER
     * @param user
     */
    public UserEntity registerCasualUser(UserEntity user) {
        RoleEntity role = roleRepository.findByName("USER_ROLE");
        List<RoleEntity> roles = Arrays.asList(role);

        user.setRoles(roles);
        UserEntity registeredUser = userRepository.save(user);

        log.info("User " + registeredUser.getUsername() + " successfully registered", registeredUser);

        return registeredUser;
    }

    /**
     * Soft user delete by user id
     * @param userID
     */
    public void deleteUser(Long userID) throws ChangeSetPersister.NotFoundException {
        UserEntity user = userRepository.findById(userID).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setDeletedAt(new Date());
        user.setAccountNonExpired(false);
        user.setAccountNonLocked(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(false);

        userRepository.save(user);
    }

}
