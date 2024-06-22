package serviceTests;

import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TraineeProfileDto;
import com.gym.crm.module.service.impl.TraineeServiceImpl;
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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.*;

public class TraineeServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TraineeRepo traineeRepo;

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private UserDetailsGenerator userDetailsGenerator;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterTrainee() {
        // Mock userDetailsGenerator
        String username = "testuser";
        String password = "testpassword";
        when(userDetailsGenerator.generateUsername(anyString(), anyString())).thenReturn(username);
        when(userDetailsGenerator.generatePassword()).thenReturn(password);

        // Mock userRepo save
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Test St";
        User savedUser = new User(1, firstName, lastName, username, password, true);
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // Mock traineeRepo save
        Trainee trainee = new Trainee(1, dateOfBirth, address, savedUser.getId());
        when(traineeRepo.save(any(Trainee.class))).thenReturn(trainee);

        // Call the method
        RegistrationResponseDto responseDto = traineeService.registerTrainee(firstName, lastName, dateOfBirth, address);

        // Verify interactions and assertions
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getUsername()).isEqualTo(username);
        assertThat(responseDto.getPassword()).isEqualTo(password);

        verify(userDetailsGenerator, times(1)).generateUsername(firstName, lastName);
        verify(userDetailsGenerator, times(1)).generatePassword();
        verify(userRepo, times(1)).save(any(User.class));
        verify(traineeRepo, times(1)).save(any(Trainee.class));
    }

    @Test
    void testGetTraineeProfile() {
        // Mock userRepo findByUsername
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Mock trainerRepo findTrainersOfTrainee
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        when(trainerRepo.findTrainersOfTrainee(username)).thenReturn(trainers);

        // Call the method
        TraineeProfileDto profileDto = traineeService.getTraineeProfile(username);

        // Verify interactions and assertions
        assertThat(profileDto).isNotNull();
        assertThat(profileDto.getUser()).isEqualTo(user);
        assertThat(profileDto.getTrainers()).isEqualTo(trainers);

        verify(userRepo, times(1)).findByUsername(username);
        verify(trainerRepo, times(1)).findTrainersOfTrainee(username);
    }

    @Test
    void testUpdateTrainee() {
        // Mock userRepo findByUsername and save
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Mock traineeRepo findByUsername and save
        Trainee trainee = new Trainee(1, new Date(), "123 Test St", user.getId());
        when(traineeRepo.findByUsername(username)).thenReturn(trainee);

        // Call the method
        traineeService.updateTrainee(username, "Jane", "Smith", new Date(), "456 New St", false);

        // Verify interactions and assertions
        verify(userRepo, times(1)).findByUsername(username);
        verify(traineeRepo, times(1)).findByUsername(username);
        verify(userRepo, times(1)).save(any(User.class));
        verify(traineeRepo, times(1)).save(any(Trainee.class));
    }

    @Test
    void testUpdateActivity() {
        // Mock userRepo findByUsername and save
        String username = "testuser";
        User user = new User(1, "John", "Doe", username, "testpassword", true);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // Call the method
        traineeService.updateActivity(username, false);

        // Verify interactions and assertions
        verify(userRepo, times(1)).findByUsername(username);
    }

    @Test
    void testDeleteTrainee() {
        // Mock traineeRepo findByUsername and deleteById
        String username = "testuser";
        Trainee trainee = new Trainee(1, new Date(), "123 Test St", 1);
        when(traineeRepo.findByUsername(username)).thenReturn(trainee);

        // Call the method
        traineeService.deleteTrainee(username);

        // Verify interactions and assertions
        verify(traineeRepo, times(1)).findByUsername(username);
        verify(traineeRepo, times(1)).deleteById(Long.valueOf(trainee.getId()));
    }
}
