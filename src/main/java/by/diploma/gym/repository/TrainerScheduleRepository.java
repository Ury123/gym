package by.diploma.gym.repository;

import by.diploma.gym.model.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, UUID> {
    List<TrainerSchedule> findByTrainerInfoId(UUID trainerInfoId);
    List<TrainerSchedule> findByGymInfoId(UUID gymInfoId);
    List<TrainerSchedule> findBYUserId(UUID userId);
    List<TrainerSchedule> findByStartDateTimeAfterAndEndDateTimeBefore(LocalDateTime start, LocalDateTime end);
}
