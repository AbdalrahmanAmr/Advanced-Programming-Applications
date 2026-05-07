package dev.mr3.sb.Dto;

public class BookAppointmentDto {
    private Long doctorId;
    private String weekday;
    private String appointmentTime;

    public BookAppointmentDto() {}

    public Long getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    public String getWeekday() {
        return weekday;
    }
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
