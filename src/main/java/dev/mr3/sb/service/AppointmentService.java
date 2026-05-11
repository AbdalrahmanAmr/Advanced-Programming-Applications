package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.Status;
import dev.mr3.sb.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

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
            return appointmentRepository.save(appointment);
        }
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
            appointment.setStatus(newStatus);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

   public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
