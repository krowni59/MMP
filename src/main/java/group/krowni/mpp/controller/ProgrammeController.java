package group.krowni.mpp.controller;

import group.krowni.mpp.dto.ProgrammeDto;
import group.krowni.mpp.entity.Programme;
import group.krowni.mpp.service.ProgrammeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/programmes")
public class ProgrammeController {

    private final ProgrammeService service;

    public ProgrammeController(ProgrammeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Programme> create(
            @RequestBody ProgrammeDto dto,
            @AuthenticationPrincipal UserDetails ud) {

        Programme p = service.create(dto, ud.getUsername());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Programme>> getAllByUser(
            @AuthenticationPrincipal UserDetails ud,
            Pageable pageable) {

        return ResponseEntity.ok(
                service.getAllByUser(ud.getUsername(), pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programme> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails ud) {

        return ResponseEntity.ok(
                service.findById(id, ud.getUsername())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Programme> update(
            @PathVariable Long id,
            @RequestBody ProgrammeDto dto,
            @AuthenticationPrincipal UserDetails ud) {

        Programme updated = service.update(id, dto, ud.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails ud) {

        service.delete(id, ud.getUsername());
        return ResponseEntity.noContent().build();
    }
}
