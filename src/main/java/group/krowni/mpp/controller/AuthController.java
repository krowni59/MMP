package group.krowni.mpp.controller;

import group.krowni.mpp.dto.LoginDto;
import group.krowni.mpp.dto.UserRegistrationDto;
import group.krowni.mpp.entity.User;
import group.krowni.mpp.security.JwtTokenProvider;
import group.krowni.mpp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Enregistrement : on crée d'abord l'utilisateur (avec encodage du mot de passe),
     * puis on génère un JWT pour le connecter directement après.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto dto) {
        // 1. Enregistrer l'utilisateur
        User user = userService.registerUser(dto);

        // 2. Construire l'objet Authentication manuellement
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
        // On le place dans le contexte (optionnel si pas besoin immediat)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generer un token JWT
        String token = tokenProvider.generateToken(authentication);

        // 4. Retourner l'email et le token
        return ResponseEntity.ok(Map.of(
                "email", user.getEmail(),
                "token", token
        ));
    }

    /**
     * Login : on delegue l’authentification a Spring Security via AuthenticationManager,
     * puis on genere et renvoie le JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(Map.of(
                "email", auth.getName(),
                "token", token
        ));
    }

}
