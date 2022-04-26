package com.techelevator.controller;

import com.techelevator.dao.AppointmentDao;
import com.techelevator.model.Appointment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AppointmentController {

    private AppointmentDao appointmentDao;

    public AppointmentController(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @RequestMapping(path = "/appointments", method = RequestMethod.POST)
    public void createAppointment(@RequestBody Appointment appointment){
        appointmentDao.createAppointment(appointment);
    }

    // All appointments
    @RequestMapping(path= "/appointments", method = RequestMethod.GET)
    public List<Appointment> getAppointments (){
        return appointmentDao.getAppointments();
    }

    @RequestMapping(path= "/availableAppointments", method = RequestMethod.GET)
    public List<Appointment> availableAppointments() {
        return appointmentDao.allAvailableAppointments();
    }

    @RequestMapping(path= "/appointments/available", method=RequestMethod.GET)
    public List <Appointment> bookedAppointments(@PathVariable String available){
        return appointmentDao.getAppointments();
    }
    @RequestMapping(path= "/offices/{officeId}/availableAppointments", method=RequestMethod.GET)
    public List<Appointment> getAptByOfficeId(@PathVariable Long officeId){
        return appointmentDao.getAptByOfficeId(officeId);
    }

    @RequestMapping(path ="/appointments", method =RequestMethod.PUT)
    public void scheduleAppointment(@RequestBody Appointment appointment){
        appointmentDao.scheduleAppointment(appointment);
    }
    @RequestMapping(path ="/user/{userId}/appointments", method = RequestMethod.GET)
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId){
        return appointmentDao.getAppointmentsByUserId(userId);
    }





}
