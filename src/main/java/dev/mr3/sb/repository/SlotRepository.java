package dev.mr3.sb.repository;

import dev.mr3.sb.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}