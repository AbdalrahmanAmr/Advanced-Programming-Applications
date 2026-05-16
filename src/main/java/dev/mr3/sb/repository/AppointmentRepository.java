package dev.mr3.sb.repository;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    List<Appointment> findByPatientPersonId(Long patientId);
    List<Appointment> findByDoctorPersonId(Long doctorId);
    List<Appointment> findByStatus(Status status);

    List<Appointment> findByDoctorPersonIdAndStatus(Long doctorId, Status status);
    List<Appointment> findByPatientPersonIdAndStatus(Long patientId, Status status);

}