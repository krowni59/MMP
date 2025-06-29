package group.krowni.mpp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "seance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seance_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    // clef = l'entité Exercise, valeur = nombre_rep
    @ElementCollection
    @CollectionTable(
            name = "seance_exercises",
            joinColumns = @JoinColumn(name = "seance_id")
    )
    @MapKeyJoinColumn(name = "exercise_id")
    @Column(name = "nombre_rep", nullable = false)
    private Map<Exercise, Integer> reps = new HashMap<>();

    /**
     * Proprietaire / Créateur de la seance
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getSeance_id() {
        return seance_id;
    }

    public void setSeance_id(Long seance_id) {
        this.seance_id = seance_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Exercise, Integer> getReps() {
        return reps;
    }

    public void setReps(Map<Exercise, Integer> reps) {
        this.reps = reps;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
