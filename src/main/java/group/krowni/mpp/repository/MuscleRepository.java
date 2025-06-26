package group.krowni.mpp.repository;

import group.krowni.mpp.entity.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    Optional<Muscle> findByName(String name);
}
