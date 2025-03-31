package by.diploma.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_schedule")
public class TrainerSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "trainer_info_id")
    private TrainerInfo trainerInfo;

    @ManyToOne
    @JoinColumn(name = "gym_info_id")
    private GymInfo gymInfo;

    @OneToMany(mappedBy = "trainerSchedule")
    private List<Appointment> appointments;
}
