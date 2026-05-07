package dev.mr3.sb.Dto;

public class ReportDto {
    private Long appointmentId;
    private String notes;

    public ReportDto() {}

    public Long getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
