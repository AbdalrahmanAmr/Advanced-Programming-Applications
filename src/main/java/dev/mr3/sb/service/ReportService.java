package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Report;
import dev.mr3.sb.repository.AppointmentRepository;
import dev.mr3.sb.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final AppointmentRepository appointmentRepository;

    public ReportService(ReportRepository reportRepository, AppointmentRepository appointmentRepository) {
        this.reportRepository = reportRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Report createReport(Report report, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            report.setAppointment(appointment);
            return reportRepository.save(report);
        }
        return null;
    }

    public Report getReportByAppointmentId(Long appointmentId) {
        return reportRepository.findByAppointmentAppointmentId(appointmentId).orElse(null);
    }

    public List<Report> getReportsByDoctor(Long doctorId) {
        return reportRepository.findByAppointmentDoctorPersonId(doctorId);
    }

    public List<Report> getReportsByPatient(Long patientId) {
        return reportRepository.findByAppointmentPatientPersonId(patientId);
    }
    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

     public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
