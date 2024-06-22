package serviceTests;

import com.gym.crm.module.entity.TrainingType;
import com.gym.crm.module.repository.TrainingTypeRepo;
import com.gym.crm.module.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepo trainingTypeRepo;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrainingTypes() {
        // Given
        TrainingType type1 = new TrainingType(1, "Cardio");
        TrainingType type2 = new TrainingType(2, "Weightlifting");
        List<TrainingType> trainingTypes = Arrays.asList(type1, type2);

        // Mock repository method
        when(trainingTypeRepo.findAll()).thenReturn(trainingTypes);

        // When
        List<TrainingType> result = trainingTypeService.getAllTrainingTypes();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Cardio");
        assertThat(result.get(1).getName()).isEqualTo("Weightlifting");
    }
}

