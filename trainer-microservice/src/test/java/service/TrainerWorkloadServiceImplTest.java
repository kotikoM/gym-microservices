package service;

import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.repository.TrainerWorkloadRepositoryMongo;
import com.gym.crm.microservice.service.impl.TrainerWorkloadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainerWorkloadServiceImplTest {

    @Mock
    private TrainerWorkloadRepositoryMongo trainerWorkloadRepo;

    @InjectMocks
    private TrainerWorkloadServiceImpl trainerWorkloadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername() {
        // Arrange
        String username = "testuser";
        TrainerWorkload expectedTrainerWorkload = new TrainerWorkload();
        expectedTrainerWorkload.setUsername(username);
        when(trainerWorkloadRepo.findByUsername(username)).thenReturn(Optional.of(expectedTrainerWorkload));

        // Act
        Optional<TrainerWorkload> result = trainerWorkloadService.findByUsername(username);

        // Assert
        assertEquals(username, result.get().getUsername());
        verify(trainerWorkloadRepo, times(1)).findByUsername(username);
    }
}
