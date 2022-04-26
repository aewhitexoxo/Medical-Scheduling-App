package com.techelevator.dao;

import com.techelevator.model.Doctor;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public interface DoctorDao {
    public long save(Doctor newDoctor);

    void updateDoctorRelatorId(long doctorId, long userId);

    Doctor getDoctorInfoByUserName(String userName);

    List getAllDoctors();

    void deleteDoctorById(long doctorId);

    long getUserIdFromDoctorId(long doctorId);
}
