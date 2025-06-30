package group.krowni.mpp.service;

import group.krowni.mpp.dto.ProgrammeDto;
import group.krowni.mpp.entity.Programme;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProgrammeService {
    /**
     * Creer un programme complet
     * @param dto le Json a fournir
     * @param userEmail l'email de l'utilisateur connecte
     * @return le programme cree
     */
    Programme create(ProgrammeDto dto, String userEmail);

    /**
     * Modifier un programme complet
     * @param id l'id du programme a modifier
     * @param dto le Json a fournir
     * @param userEmail l'email de l'utilisateur connecte
     * @return le programme cree
     */
    Programme update(Long id, ProgrammeDto dto, String userEmail);

    /**
     * Supprimer un programme via son id
     * @param id l'id du programme
     * @param userEmail l'email de l'utilisateur connecte
     */
    void delete(Long id, String userEmail);

    /**
     * Liste de tous les programmes crees par l'utilisateur connecte
     * @param userEmail l'email de l'utilisateur
     * @param pageable la pagination
     * @return la liste des programmes
     */
    List<Programme> getAllByUser(String userEmail, Pageable pageable);

    /**
     * Retrouver un programme par son id
     * @param id l'id du programme
     * @param userEmail l'email de l'utilisateur connecte
     * @return le programme
     */
    Programme findById(Long id, String userEmail);
}