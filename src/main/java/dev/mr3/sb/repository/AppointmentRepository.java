package dev.mr3.sb.repository;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientPersonId(Long patientId);
    List<Appointment> findByDoctorPersonId(Long doctorId);
    List<Appointment> findByWeekday(Weekday weekday);
}