package com.techelevator.dao;

import com.techelevator.model.Appointment;
import com.techelevator.model.Office;
import com.techelevator.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentDao {

    public List<Appointment> getAppointments();

    void createAppointment(Appointment appointment);

    public List<Appointment> allAvailableAppointments();

    public List<Appointment> bookedAppointments();

    void scheduleAppointment(Appointment appointment);

    public List<Appointment> getAptByOfficeId(Long officeId);

    public List<Appointment> getAppointmentsByUserId(Long userId);


}
