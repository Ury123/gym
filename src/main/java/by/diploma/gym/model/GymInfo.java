package by.diploma.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gym_info")
public class GymInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Size(min = 6, max = 10)
    @Pattern(regexp = "^(8029)?[0-9]{6}$")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name ="start_weekend_time",nullable = false)
    private LocalTime startWeekendTime;

    @NotNull
    @Column(name = "end_weekend_time", nullable = false)
    private LocalTime endWeekendTime;

    @NotNull
    @Column(name = "start_week_time", nullable = false)
    private LocalTime startWeekTime;

    @NotNull
    @Column(name = "end_week_time", nullable = false)
    private LocalTime endWeekTime;

    @OneToMany(mappedBy = "gymInfo")
    private List<TrainerSchedule> trainerSchedules;
}
