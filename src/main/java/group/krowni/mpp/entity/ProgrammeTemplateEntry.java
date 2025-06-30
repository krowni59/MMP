package group.krowni.mpp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "programme_template_entry",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"programme_id","day_of_week"}))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProgrammeTemplateEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "programme_id", nullable = false)
    @JsonIgnore
    private Programme programme;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seance_id", nullable = false)
    private Seance seance;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Programme getProgramme() { return programme; }
    public void setProgramme(Programme programme) { this.programme = programme; }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }
}