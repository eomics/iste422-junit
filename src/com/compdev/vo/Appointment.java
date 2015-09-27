/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.compdev.vo;

import java.util.HashMap;

/**
 *
 * @author PraveenAdivi
 */
public class Appointment {

    private String appointmentNumber;
    private String appointmentDate;
    private String appointmentTime;
    private HashMap<String, String> testDetails;
    private String phlebotomistId;
    private String patientId;
    private String patientServiceCenterCode;
    private String physicianId;

    
    public String getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }
    
    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getAppointmentTime() {
       // return appointmentTime.subSequence(appointmentTime.length()-4,appointmentTime.length()-1 ).toString(); //Remove Seconds component from time
        return appointmentTime.substring(0, 5);
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime+":00"; // Added to accept Time in Database
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientServiceCenterCode() {
        return patientServiceCenterCode;
    }

    public void setPatientServiceCenterCode(String patientServiceCenterCode) {
        this.patientServiceCenterCode = patientServiceCenterCode;
    }

    public String getPhlebotomistId() {
        return phlebotomistId;
    }

    public void setPhlebotomistId(String phlebotomistId) {
        this.phlebotomistId = phlebotomistId;
    }

    public HashMap<String, String> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(HashMap<String, String> testDetails) {
        this.testDetails = testDetails;
    }

   
}
