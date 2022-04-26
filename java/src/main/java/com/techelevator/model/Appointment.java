package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Appointment {
    @JsonProperty("appointment_id")
    private Long appointmentId;

    @JsonProperty("appointment_date")
    private String appointmentDate;

    @JsonProperty("appointment_time")
    private String appointmentTime;

    private String reason;

    @JsonProperty("doctor_id")
    private Long doctorId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("office_id")
    private Long officeId;

    private Boolean available;

    public String getReason() { return reason; }

    public void setReason(String reason) {this.reason = reason; }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
