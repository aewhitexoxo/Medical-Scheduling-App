package com.techelevator.dao;

import com.techelevator.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDoctorDao implements DoctorDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcDoctorDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long save(Doctor newDoctor) {
        String sqlInsertDoctor = "INSERT INTO doctor(first_name, last_name, practice) VALUES (?,?,?) returning doctor_id;";
        return jdbcTemplate.queryForObject(sqlInsertDoctor, Long.class, newDoctor.getFirstName(), newDoctor.getLastName());
    }

    @Override
    public java.awt.List getAllDoctors() {
        List<Doctor> allDoctors = new ArrayList<Doctor>();
        String sqlGetAllDoctors ="SELECT * "+
                "FROM doctor;";

        SqlRowSet doctors = jdbcTemplate.queryForRowSet(sqlGetAllDoctors);
        Doctor thisDoctor = null;
        while(doctors.next()) {
            thisDoctor = new Doctor();
            thisDoctor.setDoctorId(doctors.getLong("doctor_id"));
            thisDoctor.setFirstName(doctors.getString("first_name"));
            thisDoctor.setLastName(doctors.getString("last_name"));
            allDoctors.add(thisDoctor);
        }
        return (java.awt.List) allDoctors;
    }

    @Override
    public long getUserIdFromDoctorId(long doctorId) {
        String sqlGetUserId = "SELECT user_id " +
                "FROM user_doctor " +
                "WHERE doctor_id = ?";

        SqlRowSet thisDoctorId = jdbcTemplate.queryForRowSet(sqlGetUserId, doctorId);
        long currentDoctorId = 0;
        if(thisDoctorId.next()) {
            currentDoctorId = thisDoctorId.getLong("user_id");
        }

        return currentDoctorId;
    }

    @Override
    public void deleteDoctorById(long doctorId) {
        long userId = getUserIdFromDoctorId(doctorId);
        String sqlDeleteDoctorFromDoctor ="DELETE "+
                "FROM doctor " +
                "WHERE doctor_id = ?";
        jdbcTemplate.update(sqlDeleteDoctorFromDoctor, doctorId);
        String sqlDeleteDoctorFromUserDoctor ="DELETE "+
                "FROM user_doctor " +
                "WHERE doctor_id = ?";
        jdbcTemplate.update(sqlDeleteDoctorFromUserDoctor, doctorId);
        String sqlDeleteDoctorFromUserRole ="DELETE "+
                "FROM user_role " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlDeleteDoctorFromUserRole, userId);
        String sqlDeleteDoctorFromAppUser ="DELETE "+
                "FROM app_user " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlDeleteDoctorFromAppUser, userId);
    }

    // SETS PATIENT ID TO RELATE TO USER ID IN DB
    @Override
    public void updateDoctorRelatorId(long doctorId, long userId) {
        String sqlUpdatePatientRelatorId = "UPDATE user_doctor SET doctor_id = ? " + "	WHERE user_doctor.user_id = ?";
        jdbcTemplate.update(sqlUpdatePatientRelatorId, doctorId, userId);
    }
    @Override
    public Doctor getDoctorInfoByUserName(String userName) {
        String sqlSearchForUsername ="SELECT * "+
                "FROM doctor "+
                "JOIN user_doctor ON doctor.doctor_id = user_doctor.doctor_id "+
                "JOIN app_user ON user_doctor.user_id = app_user.user_id "+
                "WHERE UPPER(user_name) = ? ";

        SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchForUsername, userName.toUpperCase());
        Doctor thisDoctor = null;
        if(user.next()) {
            thisDoctor = new Doctor();
            thisDoctor = mapRowToDoctor(user);
        }

        return thisDoctor;
    }


    public Doctor mapRowToDoctor(SqlRowSet user) {
        Doctor thisDoctor = new Doctor();

        thisDoctor.setDoctorId(user.getLong("doctor_id"));
        thisDoctor.setFirstName(user.getString("first_name"));
        thisDoctor.setLastName(user.getString("last_name"));


        return thisDoctor;
    }
}
