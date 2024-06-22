package controllerTests;

import com.gym.crm.module.controller.TraineeController;
import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TraineeProfileDto;
import com.gym.crm.module.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterTrainee() {
        // Given
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto("testuser", "password");
        when(traineeService.registerTrainee(anyString(), anyString(), any(Date.class), anyString())).thenReturn(registrationResponseDto);

        // When
        ResponseEntity<RegistrationResponseDto> responseEntity = traineeController.registerTrainee("John", "Doe", new Date(), "123 Street");

        // Then
        assertEquals(registrationResponseDto, responseEntity.getBody());
    }

    @Test
    void testGetTraineeProfile() {
        // Given
        TraineeProfileDto traineeProfileDto = new TraineeProfileDto();
        when(traineeService.getTraineeProfile(anyString())).thenReturn(traineeProfileDto);

        // When
        ResponseEntity<TraineeProfileDto> responseEntity = traineeController.getTraineeProfile("testuser");

        // Then
        assertEquals(traineeProfileDto, responseEntity.getBody());
    }

    @Test
    void testUpdateTraineeProfile() {
        // Given
        TraineeProfileDto traineeProfileDto = new TraineeProfileDto();
        when(traineeService.getTraineeProfile(anyString())).thenReturn(traineeProfileDto);

        // When
        ResponseEntity<TraineeProfileDto> responseEntity = traineeController.updateTraineeProfile("testuser", "John", "Doe", new Date(), "123 Street", true);

        // Then
        assertEquals(traineeProfileDto, responseEntity.getBody());
        verify(traineeService).updateTrainee(anyString(), anyString(), anyString(), any(Date.class), anyString(), anyBoolean());
    }

    @Test
    void testDeleteTrainee() {
        // When
        ResponseEntity<Void> responseEntity = traineeController.deleteTrainee("testuser");

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(traineeService).deleteTrainee(anyString());
    }

    @Test
    void testActivateTrainee() {
        // When
        ResponseEntity<Void> responseEntity = traineeController.activateTrainee("testuser", true);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(traineeService).updateActivity(anyString(), anyBoolean());
    }
}
