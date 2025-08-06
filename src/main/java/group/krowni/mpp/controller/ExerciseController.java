package group.krowni.mpp.controller;

import group.krowni.mpp.dto.ExerciseDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Muscle;
import group.krowni.mpp.service.ExerciseService;
import group.krowni.mpp.service.impl.ExerciseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /** 1. Liste tous les exercices */
    @GetMapping
    public ResponseEntity<List<Exercise>> getAll() {
        List<Exercise> exercises = exerciseService.findAll();
        return ResponseEntity.ok(exercises);
    }

    /** 2. Récupère un exercice par son ID */
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getById(@PathVariable Long id) {
        Exercise ex = exerciseService.findById(id);
        return ResponseEntity.ok(ex);
    }

    /** 3. Crée un nouvel exercice */
    @PostMapping("/create")
    public ResponseEntity<Exercise> create(@RequestBody ExerciseDto dto) {
        Exercise created = exerciseService.create(dto);
        // Construit l'URI de la ressource créée : /api/exercises/{id}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getExercise_id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    /** 4. Met à jour un exercice existant */
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> update(
            @PathVariable Long id,
            @RequestBody Exercise dto) {
        Exercise updated = exerciseService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** 5. Supprime un exercice */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exerciseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /** 6. Ajoute un muscle à un exercice */
    @PostMapping("/{id}/muscles")
    public ResponseEntity<Exercise> addMuscle(
            @PathVariable Long id,
            @RequestParam String muscle) {
        Exercise updated = exerciseService.addMuscle(id, muscle);
        return ResponseEntity.ok(updated);
    }

    /** 7. Retire un muscle d’un exercice */
    @DeleteMapping("/{id}/muscles")
    public ResponseEntity<Exercise> removeMuscle(
            @PathVariable Long id,
            @RequestParam String muscle) {
        Exercise updated = exerciseService.removeMuscle(id, muscle);
        return ResponseEntity.ok(updated);
    }

    /** 8. Recupere tous les muscles de la BDD */
    @GetMapping("/muscles")
    public ResponseEntity<List<Muscle>> getMuscles() {
        List<Muscle> muscles = exerciseService.findAllMuscles();
        return ResponseEntity.ok(muscles);
    }
}