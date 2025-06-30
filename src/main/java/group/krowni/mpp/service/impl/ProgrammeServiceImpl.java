package group.krowni.mpp.service.impl;

import group.krowni.mpp.dto.ProgrammeDto;
import group.krowni.mpp.dto.ProgrammeTemplateEntryDto;
import group.krowni.mpp.entity.Programme;
import group.krowni.mpp.entity.ProgrammeTemplateEntry;
import group.krowni.mpp.entity.Seance;
import group.krowni.mpp.entity.User;
import group.krowni.mpp.repository.ProgrammeRepository;
import group.krowni.mpp.repository.ProgrammeTemplateEntryRepository;
import group.krowni.mpp.repository.SeanceRepository;
import group.krowni.mpp.service.ProgrammeService;
import group.krowni.mpp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProgrammeServiceImpl implements ProgrammeService {

    private final ProgrammeRepository programmeRepository;
    private final SeanceRepository seanceRepository;
    private final UserService userService;
    private final ProgrammeTemplateEntryRepository templateRepo;

    public ProgrammeServiceImpl(
            ProgrammeRepository programmeRepository,
            SeanceRepository seanceRepository,
            ProgrammeTemplateEntryRepository templateRepo,
            UserService userService) {
        this.programmeRepository = programmeRepository;
        this.seanceRepository = seanceRepository;
        this.templateRepo = templateRepo;
        this.userService = userService;
    }

    @Override
    public Programme create(ProgrammeDto dto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Programme p = new Programme();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setUser(user);

        for (ProgrammeTemplateEntryDto te : dto.getTemplate()) {
            Seance s = seanceRepository.findById(te.getSeanceId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Séance " + te.getSeanceId() + " introuvable"));
            ProgrammeTemplateEntry entry = new ProgrammeTemplateEntry();
            entry.setProgramme(p);
            entry.setSeance(s);
            entry.setDayOfWeek(te.getDayOfWeek());
            p.getTemplate().add(entry);
        }

        return programmeRepository.save(p);
    }

    @Override
    public Programme update(Long id, ProgrammeDto dto, String userEmail) {
        Programme p = programmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Programme " + id + " introuvable"));
        if (!p.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Accès refusé");
        }

        p.setName(dto.getName());
        p.setDescription(dto.getDescription());

        // Suppression des anciens jours et ajout des nouveaux
        p.getTemplate().clear();
        templateRepo.deleteByProgrammeId(p.getId());
        for (ProgrammeTemplateEntryDto te : dto.getTemplate()) {
            Seance s = seanceRepository.findById(te.getSeanceId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Séance " + te.getSeanceId() + " introuvable"
                    ));
            ProgrammeTemplateEntry entry = new ProgrammeTemplateEntry();
            entry.setProgramme(p);
            entry.setSeance(s);
            entry.setDayOfWeek(te.getDayOfWeek());
            p.getTemplate().add(entry);
        }

        return programmeRepository.save(p);
    }

    @Override
    public void delete(Long id, String userEmail) {
        Programme p = programmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Programme " + id + " introuvable"));
        if (!p.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Accès refusé");
        }
        programmeRepository.delete(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Programme> getAllByUser(String userEmail, Pageable pageable) {
        return programmeRepository.findByUserEmail(userEmail, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Programme findById(Long id, String userEmail) {
        Programme p = programmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Programme " + id + " introuvable"));
        if (!p.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Accès refusé");
        }
        return p;
    }
}