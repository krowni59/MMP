package group.krowni.mpp.service.impl;

import group.krowni.mpp.dto.ExerciseDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Muscle;
import group.krowni.mpp.repository.ExerciseRepository;
import group.krowni.mpp.repository.MuscleRepository;
import group.krowni.mpp.service.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, MuscleRepository muscleRepository) {
        this.exerciseRepository = exerciseRepository;
        this.muscleRepository = muscleRepository;
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise create(ExerciseDto dto) {
        // 1) On charge les muscles par leurs IDs
        Set<Muscle> muscles = new HashSet<>(muscleRepository.findAllById(dto.getMuscleIds()));
        if (muscles.size() != dto.getMuscleIds().size()) {
            throw new EntityNotFoundException("Un des muscles est introuvable");
        }
        // 2) Création et association
        Exercise ex = new Exercise();
        ex.setName(dto.getName());
        ex.setMuscles(muscles);
        // 3) Sauvegarde
        return exerciseRepository.save(ex);
    }

    @Override
    public Exercise update(Long exerciseId, Exercise exercise) {
        Exercise existing = findById(exerciseId);
        existing.setName(exercise.getName());

        // même logique : synchroniser la liste de muscles
        Set<Muscle> persisted = exercise.getMuscles().stream()
                .map(m -> muscleRepository.findByName(m.getName())
                        .orElseGet(() -> muscleRepository.save(new Muscle(m.getName()))))
                .collect(Collectors.toSet());
        existing.setMuscles(persisted);

        return exerciseRepository.save(existing);
    }

    @Override
    public void deleteById(Long exerciseId) {
        exerciseRepository.deleteById(exerciseId);
    }

    @Override
    public Exercise findByName(String name) {
        return exerciseRepository.findByName(name).orElseThrow(() -> new RuntimeException("Exercise with name [" + name + "] not found"));
    }

    @Override
    public Exercise findById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(()->new RuntimeException("Exercise with id [" + id + "] not found"));
    }

    @Override
    public Exercise addMuscle(Long exerciseId, String muscleName) {
        // 1) Charge l'exercice, ou lance 404
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exercice " + exerciseId + " introuvable")
                );

        // 2) Récupère ou crée le Muscle
        Muscle muscle = muscleRepository.findByName(muscleName)
                .orElseGet(() -> {
                    Muscle m = new Muscle();
                    m.setName(muscleName);
                    return muscleRepository.save(m);
                });

        // 3) Ajoute au Set (HashSet évite les doublons)
        boolean added = exercise.getMuscles().add(muscle);
        if (added) {
            // 4) Persiste seulement si nouveau muscle ajouté
            exercise = exerciseRepository.save(exercise);
        }

        // 5) Retourne l'entité mise à jour
        return exercise;
    }

    @Override
    public Exercise removeMuscle(Long exerciseId, String muscleName) {
        // 1) Charge l'exercice, ou lance 404
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exercice " + exerciseId + " introuvable")
                );

        // 2) Récupère ou crée le Muscle
        Muscle muscle = muscleRepository.findByName(muscleName)
                .orElseGet(() -> {
                    Muscle m = new Muscle();
                    m.setName(muscleName);
                    return muscleRepository.save(m);
                });

        // 3) Ajoute au Set (HashSet évite les doublons)
        boolean added = exercise.getMuscles().remove(muscle);
        if (added) {
            // 4) Persiste seulement si nouveau muscle ajouté
            exercise = exerciseRepository.save(exercise);
        }

        // 5) Retourne l'entité mise à jour
        return exercise;
    }

    @Override
    public List<Muscle> findAllMuscles() {
        return muscleRepository.findAll();
    }
}
