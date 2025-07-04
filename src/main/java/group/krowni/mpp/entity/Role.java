package group.krowni.mpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    // Le nom de la permission/role, ex. "ROLE_USER", "ROLE_ADMIN"
    @Column(nullable = false, unique = true)
    private String name;

    // Relation inverse avec User
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<User> users;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
