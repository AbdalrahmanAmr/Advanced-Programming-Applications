package dev.mr3.sb.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotsRepository extends JpaRepository<User, Long> {
}
