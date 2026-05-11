package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Report;
import dev.mr3.sb.repository.AppointmentRepository;
import dev.mr3.sb.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
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
            Report savedReport = reportRepository.save(report);
            logger.info("Report created: appointmentId={}, doctorId={}", appointmentId, report.getDoctor().getPersonId());
            return savedReport;
        }
        logger.warn("Report creation failed: appointment not found, appointmentId={}", appointmentId);
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
        logger.info("Report deleted: reportId={}", reportId);
    }
}
