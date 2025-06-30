package group.krowni.mpp.controller;

import group.krowni.mpp.dto.SeanceDto;
import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.Seance;
import group.krowni.mpp.entity.User;
import group.krowni.mpp.service.SeanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {
    private final SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    /** 1. Liste toutes les seances */
    @GetMapping
    public ResponseEntity<List<Seance>> getAll() {
        List<Seance> seances = seanceService.findAll();
        return ResponseEntity.ok(seances);
    }

    /** 2. Récupère une seance par son ID */
    @GetMapping("/{id}")
    public ResponseEntity<Seance> findById(@PathVariable Long id) {
        Seance seance = seanceService.findById(id);
        return ResponseEntity.ok(seance);
    }

    /** 3. Récupère une seance par son nom */
    @GetMapping("/name/{name}")
    public ResponseEntity<Seance> findByName(@PathVariable String name) {
        Seance seance = seanceService.findByName(name);
        return ResponseEntity.ok(seance);
    }

    /** 4. Récupère les séances d'un utilisateur */
    @GetMapping("/mail/{email}")
    public ResponseEntity<List<Seance>> findByUserEmail(@PathVariable String email) {
        List<Seance> seance = seanceService.findByUserEmail(email);
        return ResponseEntity.ok(seance);
    }

    /** 5. Crée un nouvel exercice */
    @PostMapping("/create")
    public ResponseEntity<Seance> create(@RequestBody SeanceDto dto, @AuthenticationPrincipal UserDetails user) {
        Seance created = seanceService.create(dto, user.getUsername());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getSeance_id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    /** 6. Met à jour une séance existante de l'utilisateur */
    @PutMapping("/{id}")
    public ResponseEntity<Seance> update(
            @PathVariable Long id,
            @RequestBody SeanceDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Seance updated = seanceService.update(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    /** 7. Supprime une séance de l'utilisateur */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        seanceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
