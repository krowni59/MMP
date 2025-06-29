package group.krowni.mpp.service.impl;

import group.krowni.mpp.dto.SeanceDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Seance;
import group.krowni.mpp.entity.User;
import group.krowni.mpp.repository.ExerciseRepository;
import group.krowni.mpp.repository.SeanceRepository;
import group.krowni.mpp.service.SeanceService;
import group.krowni.mpp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeanceServiceImpl implements SeanceService {
    private final SeanceRepository seanceRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserService userService;

    public SeanceServiceImpl(SeanceRepository seanceRepository, ExerciseRepository exerciseRepository, UserService userService) {
        this.seanceRepository = seanceRepository;
        this.exerciseRepository = exerciseRepository;
        this.userService = userService;
    }

    @Override
    public List<Seance> findAll() {
        return seanceRepository.findAll();
    }

    @Override
    public Seance create(SeanceDto dto, String userEmail) {
        // 1) Charge l'utilisateur
        User user = userService.findByEmail(userEmail);

        // 2) Vérifie que DTO fournit au moins un rep pour chaque exerciseId
        if (dto.getExerciseIds().size() != dto.getReps().size()) {
            throw new IllegalArgumentException(
                    "Chaque exercice doit avoir son nombre de répétitions"
            );
        }

        // 3) Instancie la nouvelle séance
        Seance seance = new Seance();
        seance.setName(dto.getName());
        seance.setDescription(dto.getDescription());
        seance.setUser(user);

        // 4) Remplit la map reps en une seule passe
        for (Long exoId : dto.getExerciseIds()) {
            Exercise exo = exerciseRepository.findById(exoId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Exercice " + exoId + " introuvable"
                    ));
            Integer nb = dto.getReps().get(exoId);
            seance.getReps().put(exo, nb);
        }

        // 5) Persiste et renvoie
        return seanceRepository.save(seance);
    }

    @Override
    public Seance update(Long seanceId, SeanceDto dto, String userEmail) {
        // 1) Récupère et vérifie la séance
        Seance existing = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new EntityNotFoundException("Séance " + seanceId + " introuvable"));
        if (! existing.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Accès refusé");
        }

        // 2) Mets à jour les champs simples
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        // 3) Vide la map actuelle et remplace par la nouvelle
        existing.getReps().clear();
        for (Long exoId : dto.getExerciseIds()) {
            Exercise exo = exerciseRepository.findById(exoId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Exercice " + exoId + " introuvable"
                    ));
            Integer nb = dto.getReps().get(exoId);
            existing.getReps().put(exo, nb);
        }

        // 4) Sauvegarde et retourne
        return seanceRepository.save(existing);
    }

    @Override
    public void deleteById(Long seanceId) {
        Seance seance = findById(seanceId);
        if (null != seance) {
            seanceRepository.delete(seance);
        }
    }

    @Override
    public Seance findByName(String name) {
        return seanceRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Seance non trouvee avec le nom : [" + name + "]"));
    }

    @Override
    public Seance findById(Long id) {
        return seanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seance non trouvee avec l'id : [" + id + "]"));
    }

    @Override
    public List<Seance> findByUserEmail(String userEmail) {
        return seanceRepository.findByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Seance non trouvee avec l'email : [" + userEmail + "]"));
    }
}
