package by.diploma.gym.repository;

import by.diploma.gym.model.GymInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymInfoRepository extends JpaRepository<GymInfo, UUID> {
    Optional<GymInfo> findByAddress(String address);
}
