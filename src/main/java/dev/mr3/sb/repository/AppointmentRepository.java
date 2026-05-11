package dev.mr3.sb.repository;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Status;
import dev.mr3.sb.model.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientPersonId(Long patientId);
    List<Appointment> findByDoctorPersonId(Long doctorId);
    List<Appointment> findByWeekday(Weekday weekday);
    List<Appointment> findByStatus(Status status);

    List<Appointment> findByDoctorPersonIdAndStatus(Long doctorId, Status status);
    List<Appointment> findByPatientPersonIdAndStatus(Long patientId, Status status);
    List<Appointment> findByDoctorPersonIdAndWeekday(Long doctorId, Weekday weekday);
    List<Appointment> findByDoctorPersonIdAndWeekdayAndStatus(Long doctorId, Weekday weekday, Status status);

}