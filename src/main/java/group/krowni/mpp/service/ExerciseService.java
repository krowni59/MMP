package group.krowni.mpp.service;

import group.krowni.mpp.dto.ExerciseDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Muscle;

import java.util.List;

public interface ExerciseService {
    /**
     * Retourne l'integralite des exercices
     * @return la liste en question
     */
    List<Exercise> findAll();

    /**
     * Creer un exercice complet
     * @param exercise l'exercice a creer
     * @return l'exercice cree
     */
    Exercise create(ExerciseDto exercise);

    /**
     * Met a jour un exercice complet
     * @param exerciseId l'id de l'exercice
     * @param exercise l'exercice a modifier
     * @return l'exercice (ou lance une exception si non trouve)
     */
    Exercise update(Long exerciseId, Exercise exercise);

    /**
     * Supprimer totalement un exercice
     * @param exerciseId l'id de l'exercice
     */
    void deleteById(Long exerciseId);

    /**
     * Cherche un exercice par son nom
     * @param name le nom de l'exercice
     * @return l'exercice (ou lance une exception si non trouve)
     */
    Exercise findByName(String name);

    /**
     * Cherche un exercice par son id
     * @param id l'id' de l'exercice
     * @return l'exercice (ou lance une exception si non trouve)
     */
    Exercise findById(Long id);

    /**
     * Ajouter un muscle travaille par l'exercice
     * @param exerciseId l'id du muscle
     * @param muscleName le muscle a ajouter
     * @return l'exercice (ou lance une exception si non trouve)
     */
    Exercise addMuscle(Long exerciseId, String muscleName);

    /**
     * Retirer un muscle travaille par l'exercice
     * @param exerciseId l'id du muscle
     * @param muscleName le muscle a retirer
     * @return l'exercice (ou lance une exception si non trouve)
     */
    Exercise removeMuscle(Long exerciseId, String muscleName);

    /**
     * Retourne tous les muscles de la BDD
     * @return l'exercice (ou lance une exception si non trouve)
     */
    List<Muscle> findAllMuscles();
}
