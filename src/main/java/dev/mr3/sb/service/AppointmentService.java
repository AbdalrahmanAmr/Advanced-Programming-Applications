package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.Status;
import dev.mr3.sb.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientPersonId(patientId);
    }
    
    public Appointment bookAppointment(Appointment appointment, Doctor doctor, Patient patient) {
        if (doctor != null && patient != null) {
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setStatus(Status.PENDING);
            Appointment savedAppointment = appointmentRepository.save(appointment);
            logger.info("Appointment booked: patientId={}, doctorId={}, day={}, time={}", 
                patient.getPersonId(), doctor.getPersonId(), appointment.getWeekday(), appointment.getAppointmenttime());
            return savedAppointment;
        }
        logger.warn("Booking failed: doctor or patient not found");
        return null;
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorPersonId(doctorId);
    }

    public List<Appointment> getAppointmentsByStatus(Status status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, Status newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            Status oldStatus = appointment.getStatus();
            appointment.setStatus(newStatus);
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            logger.info("Appointment status updated: appointmentId={}, oldStatus={}, newStatus={}", 
                appointmentId, oldStatus, newStatus);
            return updatedAppointment;
        }
        return null;
    }
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

   public void deleteAppointment(Long appointmentId) {
       Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
       if (appointment != null) {
           appointmentRepository.delete(appointment);}
        logger.info("Appointment cancelled: appointmentId={}", appointmentId);
    }
}
