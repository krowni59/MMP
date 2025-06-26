package group.krowni.mpp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "muscle")
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long muscle_id;

    @Column(nullable = false, unique = true)
    private String name;

    // constructeurs, getters / setters
    public Muscle() {}
    public Muscle(String name) { this.name = name; }

    public Long getMuscle_id() {
        return muscle_id;
    }

    public void setMuscle_id(Long muscle_id) {
        this.muscle_id = muscle_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
