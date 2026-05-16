package dev.mr3.sb.repository;

import dev.mr3.sb.model.Appointment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AppointmentSpecification {
    public static Specification<Appointment> hasDoctorName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) return null;
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("doctor").get("firstName")), "%" + name.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("doctor").get("lastName")), "%" + name.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Appointment> hasPatientName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) return null;
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("patient").get("firstName")), "%" + name.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("patient").get("lastName")), "%" + name.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Appointment> onDate(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return (root, query, criteriaBuilder) -> {
            if (dateStart == null || dateEnd == null) return null;
            return criteriaBuilder.between(root.get("appointmentTime"), dateStart, dateEnd);
        };
    }
}
