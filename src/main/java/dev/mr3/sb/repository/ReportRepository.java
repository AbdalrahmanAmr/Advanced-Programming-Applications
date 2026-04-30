package dev.mr3.sb.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: Convert to a Spring Data repository for Report persistence.
public interface ReportRepository extends JpaRepository<User, Long> {
}
