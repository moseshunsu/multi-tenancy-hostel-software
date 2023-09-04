package net.hostelHub.repository;

import net.hostelHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameOrEmail(String username, String email);
     Optional<User> findByUsernameOrEmail(String username, String email);
}
