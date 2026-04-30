package dev.mr3.sb.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: Convert to a Spring Data repository for Injury persistence.
public interface UserRepository extends JpaRepository<User, Long> {
}
