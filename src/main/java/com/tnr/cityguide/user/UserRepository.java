package com.tnr.cityguide.user;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,UUID> {
 Optional<User> findByEmailIgnoreCase(String email);
 boolean existsByEmailIgnoreCase(String email);
 boolean existsByRole(Role role);
 Optional<User> findByIdAndRole(UUID id,Role role);
}
