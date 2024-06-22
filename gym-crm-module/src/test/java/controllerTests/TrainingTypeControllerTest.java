package controllerTests;

import com.gym.crm.module.controller.TrainingTypeController;
import com.gym.crm.module.entity.TrainingType;
import com.gym.crm.module.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrainingTypes() {
        // Given
        List<TrainingType> trainingTypes = new ArrayList<>();
        TrainingType trainingType1 = new TrainingType(1, "Type 1");
        TrainingType trainingType2 = new TrainingType(2, "Type 2");
        trainingTypes.add(trainingType1);
        trainingTypes.add(trainingType2);

        when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypes);

        // When
        ResponseEntity<List<TrainingType>> responseEntity = trainingTypeController.getAllTrainingTypes();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainingTypes, responseEntity.getBody());
    }
}
