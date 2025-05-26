package group.krowni.mpp.repository;

import group.krowni.mpp.entity.Role;
import group.krowni.mpp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findByUsers(Set<User> users);
}
