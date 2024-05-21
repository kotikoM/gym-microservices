package com.gym.crm.module.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "training")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "trainee_id")
    private Integer traineeId;
    @Column(name = "trainer_id")
    private Integer trainerId;
    @Column(name = "name")
    private String name;
    @Column(name = "training_type_id")
    private Integer trainingTypeId;
    @Column(name = "date")
    private Date date;
    @Column(name = "duration")
    private Integer duration;
}
