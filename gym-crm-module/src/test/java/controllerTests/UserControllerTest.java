package controllerTests;

import com.gym.crm.module.controller.UserController;
import com.gym.crm.module.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Given
        String username = "testuser";
        String password = "testpassword";
        when(userService.checkPassword(anyString(), anyString())).thenReturn(true);

        // When
        ResponseEntity<String> responseEntity = userController.login(username, password);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void testLogin_Unauthorized() {
        // Given
        String username = "testuser";
        String password = "wrongpassword";
        when(userService.checkPassword(anyString(), anyString())).thenReturn(false);

        // When
        ResponseEntity<String> responseEntity = userController.login(username, password);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid username or password", responseEntity.getBody());
    }

    @Test
    void testChangePassword() {
        // When
        ResponseEntity<Void> responseEntity = userController.changeLogin("testuser", "oldpassword", "newpassword");

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
