package dev.mr3.sb.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookAppointmentDto {

    @NotNull(message = "Please select a doctor")
    private Long doctorId;

    @NotBlank(message = "Please select a day of the week")
    private String weekday;

    @NotBlank(message = "Please provide an appointment time")
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
