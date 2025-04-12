package by.diploma.gym.repository;

import by.diploma.gym.model.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, UUID> {
}
