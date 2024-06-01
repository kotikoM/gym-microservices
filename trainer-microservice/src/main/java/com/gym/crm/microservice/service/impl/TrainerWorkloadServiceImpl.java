package com.gym.crm.microservice.service.impl;

import com.gym.crm.microservice.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.microservice.constant.ActionType;
import com.gym.crm.microservice.model.MonthlySummary;
import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.model.YearlySummary;
import com.gym.crm.microservice.repository.TrainerWorkloadRepositoryMongo;
import com.gym.crm.microservice.service.TrainerWorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerWorkloadServiceImpl implements TrainerWorkloadService {

    @Autowired
    private TrainerWorkloadRepositoryMongo trainerWorkloadRepo;

    public List<TrainerWorkload> findAll(){
        return trainerWorkloadRepo.findAll();
    }


    @Override
    public TrainerWorkload findByUsername(String username) {
        return trainerWorkloadRepo.findByUsername(username)
                .orElse(null);
    }

    @Override
    public void handleTrainerWorkload(TrainerWorkloadRequestDTO request) {
        String username = request.getUsername();
        Optional<TrainerWorkload> trainerOptional = trainerWorkloadRepo.findByUsername(username);
        TrainerWorkload trainerWorkload = getTrainerWorkload(request, trainerOptional);

        YearMonth yearMonth = YearMonth.from(request.getTrainingDate());

        YearlySummary yearlySummary = getYearlySummary(trainerWorkload, yearMonth);
        MonthlySummary monthlySummary = getMonthlySummary(yearlySummary, yearMonth);

        updateMonthlySummary(request, monthlySummary);
        trainerWorkloadRepo.save(trainerWorkload);
    }



    private TrainerWorkload getTrainerWorkload(TrainerWorkloadRequestDTO requestDto,
                                               Optional<TrainerWorkload> trainerOptional) {
        TrainerWorkload trainer;

        if (trainerOptional.isPresent()) {
            trainer = trainerOptional.get();
        } else {
            trainer = new TrainerWorkload();
            trainer.setUsername(requestDto.getUsername());
            trainer.setFirstName(requestDto.getFirstName());
            trainer.setLastName(requestDto.getLastName());
            trainer.setStatus(requestDto.getIsActive());
            trainer.setYears(new HashSet<>());
        }
        return trainer;
    }

    private YearlySummary getYearlySummary(TrainerWorkload trainerWorkload, YearMonth yearMonth) {
        return trainerWorkload.getYears()
                .stream()
                .filter(y -> y.getYear() == yearMonth.getYear())
                .findFirst()
                .orElseGet(() -> {
                    YearlySummary ys = new YearlySummary();
                    ys.setYear(yearMonth.getYear());
                    ys.setMonths(new HashSet<>());
                    trainerWorkload.getYears().add(ys);
                    return ys;
                });
    }

    private void updateMonthlySummary(TrainerWorkloadRequestDTO request, MonthlySummary monthlySummary) {
        ActionType actionType = request.getActionType();
        if (actionType.equals(ActionType.ADD)) {
            monthlySummary.setWorkMinutes(monthlySummary.getWorkMinutes() + request.getDurationMin());
        } else if (actionType.equals(ActionType.DELETE)) {
            int minutes = Math.min(0, (monthlySummary.getWorkMinutes() - request.getDurationMin()));
            monthlySummary.setWorkMinutes(minutes);
        }
    }

    private MonthlySummary getMonthlySummary(YearlySummary yearlySummary, YearMonth yearMonth) {
        return yearlySummary.getMonths()
                .stream()
                .filter(m -> m.getMonth() == yearMonth.getMonthValue())
                .findFirst()
                .orElseGet(() -> {
                    MonthlySummary ms = new MonthlySummary();
                    ms.setMonth(yearMonth.getMonth().getValue());
                    ms.setWorkMinutes(0);
                    yearlySummary.getMonths().add(ms);
                    return ms;
                });
    }

}
