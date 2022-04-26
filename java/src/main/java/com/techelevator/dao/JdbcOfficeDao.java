package com.techelevator.dao;

import com.techelevator.model.Office;
import com.techelevator.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcOfficeDao implements OfficeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcOfficeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Office> findAll() {
        List<Office> offices = new ArrayList<>();
        String sql = "select * from office";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Office office = mapRowToOffice(results);
            offices.add(office);
        }

        return offices;
    }



    @Override
    public Office getOfficeById(Long officeId) {
        String sql = "SELECT * FROM office WHERE office_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeId);
        if(results.next()) {
            return mapRowToOffice(results);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateById(Long officeId,String phoneNumber, String officeHours, String doctors, String streetAddress, String city, String stateAbbreviation, String zipcode) {
        String sql = "UPDATE office SET phone_number = ?, office_hours = ?, doctors = ?, street_address = ?, city = ?, state_abbreviation = ?, zipcode = ? WHERE office_id = ?";
        jdbcTemplate.update(sql, phoneNumber, officeHours, doctors, streetAddress, city, stateAbbreviation, zipcode, officeId);

    }
    @Override
    public void create(String phoneNumber, String officeHours, String doctors, String streetAddress, String city, String stateAbbreviation, String zipcode) {
      String sql = "INSERT INTO office (phone_number, office_hours, doctors, street_address, city, state_abbreviation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, phoneNumber, officeHours, doctors, streetAddress, city, stateAbbreviation, zipcode);
    }

    private Office mapRowToOffice(SqlRowSet results) {
        Office office = new Office();
        office.setOfficeId(results.getLong("office_id"));
        office.setPhoneNumber(results.getString("phone_number"));
        office.setOfficeHours(results.getString("office_hours"));
        office.setDoctors(results.getString("doctors"));
        office.setStreetAddress(results.getString("street_address"));
        office.setCity(results.getString("city"));
        office.setStateAbbreviation(results.getString("state_abbreviation"));
        office.setZipcode(results.getString("zipcode"));

        return office;

    }

}
