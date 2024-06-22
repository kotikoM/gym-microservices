package controllerTests;

import com.gym.crm.module.controller.TrainingController;
import com.gym.crm.module.entity.Training;
import com.gym.crm.module.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTraining() {
        // Given
        Training inputTraining = new Training();
        inputTraining.setId(1);
        when(trainingService.createTraining(any())).thenReturn(inputTraining);

        // When
        ResponseEntity<Training> responseEntity = trainingController.createTraining(inputTraining);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(inputTraining, responseEntity.getBody());
    }

    @Test
    void testGetTraineeTrainings() {
        // Given
        String userName = "testuser";
        Date fromDate = new Date();
        Date toDate = new Date();
        String trainerName = "testtrainer";
        Integer trainingTypeId = 1;
        List<Training> trainings = new ArrayList<>();
        when(trainingService.getTraineeTrainingsByCriteria(anyString(), any(), any(), anyString(), anyInt())).thenReturn(trainings);

        // When
        ResponseEntity<List<Training>> responseEntity = trainingController.getTraineeTrainings(userName, fromDate, toDate, trainerName, trainingTypeId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainings, responseEntity.getBody());
    }

    @Test
    void testGetTrainerTrainings() {
        // Given
        String userName = "testuser";
        Date fromDate = new Date();
        Date toDate = new Date();
        String trainerName = "testtrainer";
        Integer trainingTypeId = 1;
        List<Training> trainings = new ArrayList<>();
        when(trainingService.getTrainerTrainingsByCriteria(anyString(), any(), any(), anyString(), anyInt())).thenReturn(trainings);

        // When
        ResponseEntity<List<Training>> responseEntity = trainingController.getTrainerTrainings(userName, fromDate, toDate, trainerName, trainingTypeId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainings, responseEntity.getBody());
    }
}
