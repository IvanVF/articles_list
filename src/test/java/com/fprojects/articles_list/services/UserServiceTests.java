package com.fprojects.articles_list.services;

import com.fprojects.articles_list.entities.RoleEntity;
import com.fprojects.articles_list.entities.UserEntity;
import com.fprojects.articles_list.exceptions.ResourceNotFoundException;
import com.fprojects.articles_list.repositories.RoleRepository;
import com.fprojects.articles_list.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @Test
    public void getUserByEmailTest() throws ResourceNotFoundException {
        List<UserEntity> users = generateUsers();
        UserEntity user1 = users.get(0);
        UserEntity user2 = users.get(1);

        RoleRepository roleRepository = mock(RoleRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail("user1@mail.com")).thenReturn(user1);
        when(userRepository.findByEmail("user2@mail.com")).thenReturn(user2);

        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);

        assertEquals(userService.getUserByEmail("user1@mail.com").getEmail(), "user1@mail.com");
        assertEquals(userService.getUserByEmail("user2@mail.com").getEmail(), "user2@mail.com");
    }

    @Test
    public void getUserByUsernameTest() {
        List<UserEntity> users = generateUsers();
        UserEntity user1 = users.get(0);
        UserEntity user2 = users.get(1);

        RoleRepository roleRepository = mock(RoleRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername("user1")).thenReturn(user1);
        when(userRepository.findByUsername("user2")).thenReturn(user2);

        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);

        assertEquals(userService.getUserByUsername("user1").getEmail(), "user1@mail.com");
        assertEquals(userService.getUserByUsername("user2").getEmail(), "user2@mail.com");
    }

    @Test
    public void registerCasualUserTest() {
        List<UserEntity> users1 = generateUsers();
        List<UserEntity> users2 = generateUsers();
        UserEntity user1 = users1.get(0);
        UserEntity user2 = users2.get(0);
        String rawPassword = user1.getPassword();

        RoleRepository roleRepository = mock(RoleRepository.class);
        when(roleRepository.findByName("USER_ROLE")).thenReturn(new RoleEntity("USER_ROLE", Collections.singletonList(user1)));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(user1)).thenReturn(user2);

        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);
        UserEntity savedUser = userService.registerCasualUser(user1);

        assertTrue(passwordEncoder.matches(rawPassword, user1.getPassword()));
        assertEquals(savedUser.getEmail(), user1.getEmail());

    }

    private List<UserEntity> generateUsers() {
        UserEntity user1 = new UserEntity("user1", "user1@mail.com", "100",
                Collections.singletonList(new RoleEntity("ADMIN_ROLE", Collections.singletonList(new UserEntity()))),
                true, true, true, true);
        user1.setId(1L);
        UserEntity user2 = new UserEntity("user2", "user2@mail.com", "200",
                Collections.singletonList(new RoleEntity("USER_ROLE", Collections.singletonList(new UserEntity()))),
                true, true, true, true);
        user2.setId(2L);

        return Arrays.asList(user1, user2);
    }

}
