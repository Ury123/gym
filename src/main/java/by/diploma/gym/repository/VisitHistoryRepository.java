package by.diploma.gym.repository;

import by.diploma.gym.model.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitHistoryRepository extends JpaRepository<VisitHistory, UUID> {
    List<VisitHistory> findByUserId(UUID userId);
}
