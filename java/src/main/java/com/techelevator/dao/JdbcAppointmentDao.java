package com.techelevator.dao;

import com.techelevator.model.Appointment;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAppointmentDao implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAppointmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Appointment> getAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointment";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()){
            Appointment appointment = mapRowToAppointment(rowSet);
            appointments.add(appointment);
        }
        return appointments;
    }

    public void createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (appointment_date,appointment_time,doctor_id,patient_id,office_id) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, appointment.getAppointmentDate(),appointment.getAppointmentTime(),appointment.getDoctorId(),appointment.getUserId(),appointment.getOfficeId());
    }

    @Override
    public List<Appointment> allAvailableAppointments() {
        List<Appointment> availableAppointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment \n" +
                "WHERE available = true;\n";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            availableAppointments.add(appointment);
        }

        return availableAppointments;
    }

    @Override
    public List<Appointment> bookedAppointments() {
        List<Appointment> bookedAppointments = new ArrayList<>();
        String sql = "SELECT available FROM appointment WHERE appointment_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Appointment appointment = mapRowToAppointment(results);
           bookedAppointments.add(appointment);
        }

        return bookedAppointments;

    }

    @Override
    public void scheduleAppointment(Appointment appointment) {
        String sql = "UPDATE appointment SET appointment_id = ?,appointment_date = ?,appointment_time = ?, reason = ?, doctor_id = ?, user_id = ?, office_id = ?, available = false  WHERE appointment_id = ?";
        jdbcTemplate.update(sql, appointment.getAppointmentId(), appointment.getAppointmentDate(),appointment.getAppointmentTime(), appointment.getReason(), appointment.getDoctorId(),appointment.getUserId(),appointment.getOfficeId(),appointment.getAppointmentId());
    }

    @Override
    public List<Appointment> getAptByOfficeId(Long officeId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE office_id = ? AND available = true";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeId);

        while(results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        } return appointments;
        }

    @Override
    public List<Appointment> getAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE user_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

        while(result.next()){
            Appointment appointment = mapRowToAppointment(result);
            appointments.add(appointment);
        }


        return appointments;
    }


    private Appointment mapRowToAppointment(SqlRowSet rowSet) {

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rowSet.getLong("appointment_id"));
        appointment.setAppointmentDate(rowSet.getString("appointment_date"));
        appointment.setAppointmentTime(rowSet.getString("appointment_time"));
        appointment.setReason(rowSet.getString("reason"));
        appointment.setDoctorId(rowSet.getLong("doctor_id"));
        appointment.setUserId(rowSet.getLong("user_id"));
        appointment.setOfficeId(rowSet.getLong("office_id"));
        appointment.setAvailable(rowSet.getBoolean("available"));

        return appointment;
    }
}
