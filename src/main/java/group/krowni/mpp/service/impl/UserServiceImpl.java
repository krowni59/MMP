package group.krowni.mpp.service.impl;

import group.krowni.mpp.dto.UserRegistrationDto;
import group.krowni.mpp.entity.Role;
import group.krowni.mpp.entity.User;
import group.krowni.mpp.repository.RoleRepository;
import group.krowni.mpp.repository.UserRepository;
import group.krowni.mpp.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository      = userRepository;
        this.roleRepository      = roleRepository;
        this.passwordEncoder     = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegistrationDto dto) {
        // 1. Vérifier que le username n'existe pas déjà
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username déjà pris");
        }

        // 2. Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 3. Créer l'entité User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        if (null != dto.getPhone()) {
            user.setPhone(dto.getPhone());
        }
        user.setEnabled(true);

        // 4. Récupérer (ou créer) le rôle par défaut (ROLE_USER)
        // TODO : REFAIRE LA GESTION DES ROLES
        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role("ROLE_USER");
                    return roleRepository.save(role);
                });

        user.setRoles(Collections.singleton(userRole));

        // 5. Sauvegarder en base
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable !"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utilisateur introuvable pour l’email : " + email
                        )
                );
    }


    @Override
    public String disableUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable !"));
        user.setEnabled(false);
        return "L'utilisateur " + user_id + " a ete desactive.";
    }

    @Override
    public String enableUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable !"));
        user.setEnabled(true);
        return "L'utilisateur " + user_id + " a ete reactive.";
    }

    @Override
    public User addRoleToUser(Long user_id) {
        // TODO : FAIRE GESTION DES ROLES
        return null;
    }

    @Override
    public User removeRoleToUser(Long user_id) {
        // TODO : FAIRE GESTION DES ROLES
        return null;
    }
}
