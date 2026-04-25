package dev.mr3.sb.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: Convert to a Spring Data repository for User persistence.
public interface PatientRepository extends JpaRepository<User, Long> {
}
