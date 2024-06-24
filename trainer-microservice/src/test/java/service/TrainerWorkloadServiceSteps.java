package service;

import com.gym.crm.microservice.constant.ActionType;
import com.gym.crm.microservice.dto.TrainerWorkloadRequestDto;
import com.gym.crm.microservice.model.MonthlySummary;
import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.model.YearlySummary;
import com.gym.crm.microservice.repository.TrainerWorkloadRepositoryMongo;
import com.gym.crm.microservice.service.TrainerWorkloadService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
public class TrainerWorkloadServiceSteps {
    @Autowired
    private TrainerWorkloadService trainerWorkloadService;
    @Autowired
    private TrainerWorkloadRepositoryMongo trainerWorkloadRepository;

    private TrainerWorkloadRequestDto requestDto;
    private TrainerWorkload savedTrainerWorkload;

    @Before
    public void cleanDatabase() {
        trainerWorkloadRepository.deleteAll();
    }

    @Given("the database contains a trainer with username {string} and the following yearly summaries:")
    public void the_database_contains_a_trainer_with_username_and_the_following_yearly_summaries(String username, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> yearlySummaries = dataTable.asMaps(String.class, String.class);

        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername(username);
        trainer.setYears(new HashSet<>());

        for (Map<String, String> summary : yearlySummaries) {
            int year = Integer.parseInt(summary.get("year"));
            String month = summary.get("month");
            int workingMinutes = Integer.parseInt(summary.get("workingMinutes"));

            YearlySummary yearlySummary = new YearlySummary();
            yearlySummary.setYear(year);
            MonthlySummary monthlySummary = new MonthlySummary();
            monthlySummary.setMonth(YearMonth.parse(month).getMonthValue());
            monthlySummary.setWorkMinutes(workingMinutes);
            yearlySummary.setMonths(new HashSet<>());
            yearlySummary.getMonths().add(monthlySummary);
            trainer.getYears().add(yearlySummary);
        }

        trainerWorkloadRepository.save(trainer);
    }

    @When("the workload is added with the following details:")
    public void the_workload_is_added_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        requestDto = createTrainerWorkloadRequestDto(dataTable);
        trainerWorkloadService.handleTrainerWorkload(requestDto);
        savedTrainerWorkload = trainerWorkloadRepository.findByUsername(requestDto.getUsername()).orElse(null);
    }

    @When("the workload is deleted with the following details:")
    public void the_workload_is_deleted_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        requestDto = createTrainerWorkloadRequestDto(dataTable);
        requestDto.setActionType(ActionType.DELETE);
        trainerWorkloadService.handleTrainerWorkload(requestDto);
        savedTrainerWorkload = trainerWorkloadRepository.findByUsername(requestDto.getUsername()).orElse(null);
    }

    @Then("the trainer's monthly summary for JANUARY {int} should be updated to {int} minutes")
    public void the_trainer_s_monthly_summary_for_january_should_be_updated_to_minutes(Integer year, Integer expectedMinutes) {
        System.out.println(savedTrainerWorkload.toString());
        assertNotNull(savedTrainerWorkload);

        Optional<YearlySummary> yearlySummaryOptional = savedTrainerWorkload.getYears().stream().findFirst();
        assertTrue("Yearly summary for year " + year + " not found", yearlySummaryOptional.isPresent());


        Optional<MonthlySummary> januarySummaryOptional = yearlySummaryOptional.get().getMonths().stream().findFirst();
        assertTrue("Monthly summary for January " + year + " not found", januarySummaryOptional.isPresent());


        assertEquals("Unexpected monthly working minutes", expectedMinutes, januarySummaryOptional.get().getWorkMinutes());
    }


    private TrainerWorkloadRequestDto createTrainerWorkloadRequestDto(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        TrainerWorkloadRequestDto requestDto = new TrainerWorkloadRequestDto();
        requestDto.setUsername(data.get(0).get("username"));
        requestDto.setTrainingDate(LocalDate.parse(data.get(0).get("trainingDate")));
        requestDto.setDurationMin(Integer.parseInt(data.get(0).get("durationMinutes")));
        requestDto.setActionType(ActionType.valueOf(data.get(0).get("actionType")));
        return requestDto;
    }

}
