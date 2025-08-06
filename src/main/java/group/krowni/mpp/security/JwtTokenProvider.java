package group.krowni.mpp.security;

import group.krowni.mpp.entity.User;
import group.krowni.mpp.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final UserService userService;
    @Value("${app.jwt.secret}")
    private String jwtSecret;              // Base64-encoded

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationInMs;

    private Key key;

    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        byte[] decoded = Base64.getDecoder().decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(decoded);
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByEmail(username);
        Date now       = new Date();
        Date expiry    = new Date(now.getTime() + jwtExpirationInMs);

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUser_id()))
                .claim("roles", roles)
                .claim("firstName", user.getFirstName())
                .claim("username", user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String roles = claims.get("roles", String.class);
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // logger.warn("Invalid JWT token", ex);
            return false;
        }
    }
}