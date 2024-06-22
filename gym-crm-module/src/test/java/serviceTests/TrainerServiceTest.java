package serviceTests;

import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TrainerProfileDto;
import com.gym.crm.module.service.impl.TrainerServiceImpl;
import com.gym.crm.module.entity.Trainee;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.User;
import com.gym.crm.module.generator.UserDetailsGenerator;
import com.gym.crm.module.repository.TraineeRepo;
import com.gym.crm.module.repository.TrainerRepo;
import com.gym.crm.module.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TraineeRepo traineeRepo;

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private UserDetailsGenerator userDetailsGenerator;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterTrainer() {
        // Mock userDetailsGenerator
        String username = "testuser";
        String password = "testpassword";
        when(userDetailsGenerator.generateUsername(anyString(), anyString())).thenReturn(username);
        when(userDetailsGenerator.generatePassword()).thenReturn(password);

        // Mock userRepo save
        String firstName = "John";
        String lastName = "Doe";
        User savedUser = new User(1, firstName, lastName, username, password, true);
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // Mock trainerRepo save
        Integer trainingTypeId = 1;
        Trainer trainer = new Trainer(1, trainingTypeId, savedUser.getId());
        when(trainerRepo.save(any(Trainer.class))).thenReturn(trainer);

        // Call the method
        RegistrationResponseDto responseDto = trainerService.registerTrainer(firstName, lastName, trainingTypeId);

        // Verify interactions and assertions
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getUsername()).isEqualTo(username);
        assertThat(responseDto.getPassword()).isEqualTo(password);

        verify(userDetailsGenerator, times(1)).generateUsername(firstName, lastName);
        verify(userDetailsGenerator, times(1)).generatePassword();
        verify(userRepo, times(1)).save(any(User.class));
        verify(trainerRepo, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGetUnassignedTrainers() {
        // Mock trainerRepo findUnassignedTrainers
        String traineeUsername = "trainee";
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        when(trainerRepo.findUnassignedTrainers(traineeUsername)).thenReturn(trainers);

        // Call the method
        List<Trainer> result = trainerService.getUnassignedTrainers(traineeUsername);

        // Verify interactions and assertions
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(trainers.size());

        verify(trainerRepo, times(1)).findUnassignedTrainers(traineeUsername);
    }

    @Test
    void testGetTrainerProfile() {
        // Mock userRepo findByUsername
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Mock traineeRepo findTraineesOfTrainer
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        when(traineeRepo.findTraineesOfTrainer(username)).thenReturn(trainees);

        // Call the method
        TrainerProfileDto profileDto = trainerService.getTrainerProfile(username);

        // Verify interactions and assertions
        assertThat(profileDto).isNotNull();
        assertThat(profileDto.getUser()).isEqualTo(user);
        assertThat(profileDto.getTrainees()).isEqualTo(trainees);

        verify(userRepo, times(1)).findByUsername(username);
        verify(traineeRepo, times(1)).findTraineesOfTrainer(username);
    }

    @Test
    void testUpdateTrainer() {
        // Mock userRepo findByUsername and save
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Mock trainerRepo findByUsername and save
        Integer specialization = 1;
        Trainer trainer = new Trainer(1, specialization, user.getId());
        when(trainerRepo.findByUsername(username)).thenReturn(trainer);

        // Call the method
        trainerService.updateTrainer(username, "Jane", "Smith", specialization, false);

        // Verify interactions and assertions
        verify(userRepo, times(1)).findByUsername(username);
        verify(trainerRepo, times(1)).findByUsername(username);
        verify(userRepo, times(1)).save(any(User.class));
        verify(trainerRepo, times(1)).save(any(Trainer.class));
    }

    @Test
    void testUpdateActivity() {
        // Mock userRepo findByUsername and save
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Call the method
        trainerService.updateActivity(username, false);

        // Verify interactions and assertions
        verify(userRepo, times(1)).findByUsername(username);
    }
}
