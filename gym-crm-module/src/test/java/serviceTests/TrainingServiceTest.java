package serviceTests;

import com.gym.crm.module.dto.TrainerWorkloadRequestDto;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.Training;
import com.gym.crm.module.entity.User;
import com.gym.crm.module.repository.TrainerRepo;
import com.gym.crm.module.repository.TrainingRepo;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.client.TrainerWorkloadClient;
import com.gym.crm.module.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private TrainingRepo trainingRepo;

    @Mock
    private TrainerWorkloadClient trainerWorkloadClient;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTraining() {
        // Given
        Training training = new Training();
        training.setId(1);
        training.setTrainerId(1);

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setUserId(1);

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setIsActive(true);

        when(trainerRepo.findById(1L)).thenReturn(java.util.Optional.of(trainer));
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.of(user));

        // When
        trainingService.createTraining(training);

        // Then
        verify(jmsTemplate, times(1)).convertAndSend(any(String.class), any(TrainerWorkloadRequestDto.class));
        verify(trainingRepo, times(1)).save(any(Training.class));
    }

    @Test
    void testCreateTrainingTrainerNotFound() {
        // Given
        Training training = new Training();
        training.setId(1);
        training.setTrainerId(1);

        when(trainerRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        // When, Then
        verifyNoInteractions(jmsTemplate);
        verify(trainingRepo, never()).save(any(Training.class));
    }

    @Test
    void testCreateTrainingUserNotFound() {
        // Given
        Training training = new Training();
        training.setId(1);
        training.setTrainerId(1);

        Trainer trainer = new Trainer();
        trainer.setId(1);

        when(trainerRepo.findById(1L)).thenReturn(java.util.Optional.of(trainer));
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        // When, Then
        verifyNoInteractions(jmsTemplate);
        verify(trainingRepo, never()).save(any(Training.class));
    }

    @Test
    void testCallTrainerMicroService() {
        // Given
        Training training = new Training();
        training.setId(1);
        training.setTrainerId(1);
        training.setDate(LocalDate.now());
        training.setDuration(60);

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setUserId(1);

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setIsActive(true);

        when(trainerRepo.findById(1L)).thenReturn(java.util.Optional.of(trainer));
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.of(user));

        // When
        trainingService.createTraining(training);

        // Then
        verify(jmsTemplate, times(1)).convertAndSend(any(String.class), any(TrainerWorkloadRequestDto.class));
    }

}
