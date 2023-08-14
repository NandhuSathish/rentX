package com.innovature.rentx.repository;
import com.innovature.rentx.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserRepositoryTest {

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByEmail(email);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(email, result.get().getEmail());
    }

    @Test
    public void testFindByEmailAndStatus() {
        String email = "test@example.com";
        byte status = 1;
        User user = new User();
        user.setEmail(email);
        user.setStatus(status);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByEmailAndStatus(email, status)).thenReturn(user);

        User result = userRepository.findByEmailAndStatus(email, status);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getEmail());
        Assertions.assertEquals(status, result.getStatus());
    }

    @Test
    public void testFindByIdAndPassword() {
        Integer id = 1;
        String password = "password";
        User user = new User();
        user.setId(id);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByIdAndPassword(id, password)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByIdAndPassword(id, password);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(id, result.get().getId());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        User user = new User();
        user.setId(id);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findById(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(id, result.get().getId());
    }

    @Test
    public void testSave() {
        User user = new User();
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userRepository.save(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userRepository.existsByEmail(email);

        Assertions.assertTrue(result);
    }

    @Test
    public void testFindByEmailId() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByEmailId(email)).thenReturn(user);

        User result = userRepository.findByEmailId(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getEmail());
    }
}

