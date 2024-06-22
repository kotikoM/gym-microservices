package serviceTests;

import com.gym.crm.module.entity.User;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testuser");
        user.setPassword("oldpassword");
    }

    @Test
    void testGetByUsername() {
        when(userRepo.findByUsername(anyString())).thenReturn(user);

        User foundUser = userService.getByUsername("testuser");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
        verify(userRepo, times(1)).findByUsername("testuser");
    }

    @Test
    void testUpdatePasswordSuccess() {
        when(userRepo.findByUsername(anyString())).thenReturn(user);

        userService.updatePassword("testuser", "oldpassword", "newpassword");

        verify(userRepo, times(2)).findByUsername("testuser");
        verify(userRepo, times(1)).save(any(User.class));
        assertThat(user.getPassword()).isEqualTo("newpassword");
    }

    @Test
    void testUpdatePasswordFailure() {
        when(userRepo.findByUsername(anyString())).thenReturn(user);

        userService.updatePassword("testuser", "wrongpassword", "newpassword");

        verify(userRepo, times(1)).findByUsername("testuser");
        verify(userRepo, times(0)).save(any(User.class));
        assertThat(user.getPassword()).isEqualTo("oldpassword");
    }

    @Test
    void testCheckPasswordSuccess() {
        when(userRepo.findByUsername(anyString())).thenReturn(user);

        Boolean result = userService.checkPassword("testuser", "oldpassword");

        assertThat(result).isTrue();
        verify(userRepo, times(1)).findByUsername("testuser");
    }

    @Test
    void testCheckPasswordFailure() {
        when(userRepo.findByUsername(anyString())).thenReturn(user);

        Boolean result = userService.checkPassword("testuser", "wrongpassword");

        assertThat(result).isFalse();
        verify(userRepo, times(1)).findByUsername("testuser");
    }
}
