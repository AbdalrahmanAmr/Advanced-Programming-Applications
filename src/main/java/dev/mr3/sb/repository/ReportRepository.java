package dev.mr3.sb.repository;

import dev.mr3.sb.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByAppointmentAppointmentId(Long appointmentId);
    List<Report> findByAppointmentDoctorPersonId(Long doctorId);
    List<Report> findByAppointmentPatientPersonId(Long patientId);
}