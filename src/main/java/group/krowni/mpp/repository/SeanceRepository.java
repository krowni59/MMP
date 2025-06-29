package group.krowni.mpp.repository;

import group.krowni.mpp.entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    Optional<Seance> findByName(String name);
    Optional<List<Seance>> findByUserEmail(String userEmail);
}
