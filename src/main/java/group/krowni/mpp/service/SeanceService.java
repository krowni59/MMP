package group.krowni.mpp.service;

import group.krowni.mpp.dto.SeanceDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Seance;
import group.krowni.mpp.entity.User;

import java.util.List;

public interface SeanceService {
    /**
     * Retourne l'integralite des seances
     * @return la liste en question
     */
    List<Seance> findAll();

    /**
     * Cree une seance et l'assigne a un utilisateur
     * @param seance La seance a creer
     * @param userEmail l'email de l'utilisateur connecte
     * @return la liste en question
     */
    Seance create(SeanceDto seance, String userEmail);

    /**
     * Met a jour une seance complete
     * @param seanceId l'id de l'exercice
     * @param seance la seance a modifier
     * @return la seance (ou lance une exception si non trouve)
     */
    Seance update(Long seanceId, SeanceDto seance, String userEmail);

    /**
     * Supprimer totalement une seance
     * @param seanceId l'id de la seance
     */
    void deleteById(Long seanceId);

    /**
     * Cherche une seance par son nom
     * @param name le nom de la seance
     * @return la seance (ou lance une exception si non trouve)
     */
    Seance findByName(String name);

    /**
     * Cherche une seance par son id
     * @param id l'id de la seance
     * @return la seance (ou lance une exception si non trouve)
     */
    Seance findById(Long id);

    /**
     * Récupère toutes les sessions de l'utilisateur
     * @param userEmail email de l'utilisateur
     * @return liste des sessions
     */
    List<Seance> findByUserEmail(String userEmail);
}
