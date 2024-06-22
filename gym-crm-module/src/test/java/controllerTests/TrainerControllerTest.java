package controllerTests;

import com.gym.crm.module.controller.TrainerController;
import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TrainerProfileDto;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterTrainer() {
        // Given
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto("testtrainer", "password");
        when(trainerService.registerTrainer(anyString(), anyString(), anyInt())).thenReturn(registrationResponseDto);

        // When
        ResponseEntity<RegistrationResponseDto> responseEntity = trainerController.registerTrainer("John", "Doe", 1);

        // Then
        assertEquals(registrationResponseDto, responseEntity.getBody());
    }

    @Test
    void testGetTrainerProfile() {
        // Given
        TrainerProfileDto trainerProfileDto = new TrainerProfileDto();
        when(trainerService.getTrainerProfile(anyString())).thenReturn(trainerProfileDto);

        // When
        ResponseEntity<TrainerProfileDto> responseEntity = trainerController.getTrainerProfile("testtrainer");

        // Then
        assertEquals(trainerProfileDto, responseEntity.getBody());
    }

    @Test
    void testGetUnassignedTrainers() {
        // Given
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        when(trainerService.getUnassignedTrainers(anyString())).thenReturn(trainers);

        // When
        ResponseEntity<List<Trainer>> responseEntity = trainerController.getUnassignedTrainers("testtrainee");

        // Then
        assertEquals(trainers, responseEntity.getBody());
    }

    @Test
    void testUpdateTrainerProfile() {
        // Given
        TrainerProfileDto trainerProfileDto = new TrainerProfileDto();
        when(trainerService.getTrainerProfile(anyString())).thenReturn(trainerProfileDto);

        // When
        ResponseEntity<TrainerProfileDto> responseEntity = trainerController.updateTrainerProfile("testtrainer", "John", "Doe", 1, true);

        // Then
        assertEquals(trainerProfileDto, responseEntity.getBody());
        verify(trainerService).updateTrainer(anyString(), anyString(), anyString(), anyInt(), anyBoolean());
    }

    @Test
    void testActivateTrainer() {
        // When
        ResponseEntity<Void> responseEntity = trainerController.activateTrainer("testtrainer", true);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(trainerService).updateActivity(anyString(), anyBoolean());
    }
}
