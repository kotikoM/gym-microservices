package com.gym.crm.microservice.service.impl;

import com.gym.crm.microservice.dto.TrainerWorkloadRequestDto;
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
    public Optional<TrainerWorkload> findByUsername(String username) {
        return trainerWorkloadRepo.findByUsername(username);
    }

    @Override
    public void handleTrainerWorkload(TrainerWorkloadRequestDto request) {
        String username = request.getUsername();
        Optional<TrainerWorkload> trainerOptional = trainerWorkloadRepo.findByUsername(username);
        TrainerWorkload trainerWorkload = getTrainerWorkload(request, trainerOptional.orElse(null));

        YearMonth yearMonth = YearMonth.from(request.getTrainingDate());

        YearlySummary yearlySummary = getYearlySummary(trainerWorkload, yearMonth);
        MonthlySummary monthlySummary = getMonthlySummary(yearlySummary, yearMonth);

        updateMonthlySummary(request, monthlySummary);
        trainerWorkloadRepo.save(trainerWorkload);
    }



    private TrainerWorkload getTrainerWorkload(TrainerWorkloadRequestDto requestDto, TrainerWorkload trainerWorkload) {
        if (trainerWorkload == null) {
            trainerWorkload = new TrainerWorkload();
            trainerWorkload.setUsername(requestDto.getUsername());
            trainerWorkload.setFirstName(requestDto.getFirstName());
            trainerWorkload.setLastName(requestDto.getLastName());
            trainerWorkload.setStatus(requestDto.getIsActive());
            trainerWorkload.setYears(new HashSet<>());
        }
        return trainerWorkload;
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

    private void updateMonthlySummary(TrainerWorkloadRequestDto request, MonthlySummary monthlySummary) {
        ActionType actionType = request.getActionType();
        if (ActionType.ADD.equals(actionType)) {
            monthlySummary.setWorkMinutes(monthlySummary.getWorkMinutes() + request.getDurationMin());
        } else if (ActionType.DELETE.equals(actionType)) {
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
