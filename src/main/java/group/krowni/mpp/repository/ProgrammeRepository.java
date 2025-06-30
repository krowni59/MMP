package group.krowni.mpp.repository;

import group.krowni.mpp.entity.Programme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgrammeRepository extends JpaRepository<Programme,Long> {
    List<Programme> findByUserEmail(String email, Pageable pageable);
}