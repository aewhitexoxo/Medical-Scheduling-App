package com.techelevator.controller;

import com.techelevator.dao.OfficeDao;
import com.techelevator.model.Office;
import com.techelevator.model.Review;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class OfficeController {

    private OfficeDao officeDao;

    public OfficeController(OfficeDao officeDao){
        this.officeDao = officeDao;
    }

    @RequestMapping(path= "/offices", method=RequestMethod.GET)
    public List<Office> getOffices(){
        return officeDao.findAll();
    }

    @RequestMapping(path= "/offices/{officeId}", method=RequestMethod.GET)
    public Office getOfficeById(@PathVariable Long officeId){
        return officeDao.getOfficeById(officeId);
    }

    @RequestMapping(path = "offices/{officeId}/update", method=RequestMethod.PUT)
    public void updateById(@PathVariable Long officeId, @RequestBody Office office){
        officeDao.updateById(office.getOfficeId(), office.getPhoneNumber(), office.getOfficeHours(), office.getDoctors(), office.getStreetAddress(), office.getCity(), office.getStateAbbreviation(), office.getZipcode());
    }






}
