package group.krowni.mpp.service;

import group.krowni.mpp.dto.UserRegistrationDto;
import group.krowni.mpp.entity.User;

public interface UserService {
    /**
     * Enregistre un nouvel utilisateur
     * @param registrationDto informations de l'utilisateur (username, password etc)
     * @return l'utilisateur saved
     */
    User registerUser(UserRegistrationDto registrationDto);

    /**
     * Cherche un utilisateur par son username
     * @param username le login de l'utilisateur
     * @return l'utilisateur (ou lance une exception si non trouve)
     */
    User findByUsername(String username);

    /**
     * Cherche un utilisateur par son email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur (ou lance une exception si non trouve)
     */
    User findByEmail(String email);

    /**
     * Permet de desactiver un compte
     * @param user_id l'id de l'utilisateur
     * @return "L'utilisateur XX a ete desactive" (ou lance une exception si non trouve)
     */
    String disableUser(Long user_id);

    /**
     * Permet d'activer un compte
     * @param user_id l'id de l'utilisateur
     * @return "L'utilisateur XX a ete reactive" (ou lance une exception si non trouve)
     */
    String enableUser(Long user_id);

    /**
     * Permet d'ajouter un role a un utilisateur
     * @param user_id l'id de l'utilisateur
     * @return l'utilisateur (ou lance une exception si non trouve)
     */
    User addRoleToUser(Long user_id);

    /**
     * Permet de supprimer un role d'un utilisateur
     * @param user_id l'id de l'utilisateur
     * @return l'utilisateur (ou lance une exception si non trouve)
     */
    User removeRoleToUser(Long user_id);
}
