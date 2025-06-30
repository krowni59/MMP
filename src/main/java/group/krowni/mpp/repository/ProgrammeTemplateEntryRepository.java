package group.krowni.mpp.repository;

import group.krowni.mpp.entity.ProgrammeTemplateEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgrammeTemplateEntryRepository extends JpaRepository<ProgrammeTemplateEntry,Long> {
    List<ProgrammeTemplateEntry> findByProgrammeId(Long programmeId);
    void deleteByProgrammeId(Long id);
}