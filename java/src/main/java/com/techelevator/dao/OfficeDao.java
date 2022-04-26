package com.techelevator.dao;

import com.techelevator.model.Office;


import java.util.List;

public interface OfficeDao {

     List<Office> findAll();

    public Office getOfficeById(Long officeId);

    void updateById(Long officeId,String phoneNumber, String officeHours, String doctors ,  String streetAddress, String city, String stateAbbreviation, String zipcode);

    void create(String phoneNumber, String officeHours, String doctors, String streetAddress, String city, String stateAbbreviation, String zipcode);

}
