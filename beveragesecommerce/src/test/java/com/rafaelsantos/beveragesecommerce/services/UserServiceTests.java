package com.rafaelsantos.beveragesecommerce.services;

import com.rafaelsantos.beveragesecommerce.entities.User;
import com.rafaelsantos.beveragesecommerce.factories.UserDetailsFactory;
import com.rafaelsantos.beveragesecommerce.factories.UserFactory;
import com.rafaelsantos.beveragesecommerce.projections.UserDetailsProjection;
import com.rafaelsantos.beveragesecommerce.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingUsername, nonExistingUsername;
    private User user;

    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        nonExistingUsername = "user@gmail.com";

        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);

        Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        UserDetails result = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUsername);
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(nonExistingUsername));
    }
}
