/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.compdev.bo;

import com.compdev.vo.Appointment;
import com.compdev.vo.Patient;
import com.compdev.exceptions.LAMSException;

/**
 *
 * @author PraveenAdivi
 */
public interface IBookAppointment
{
public boolean checkAvailability(Appointment p_appointment)throws LAMSException;
public String makeAppointment(Appointment p_appointment)throws LAMSException;
public Appointment getNextAppointment(Appointment p_appointment)throws LAMSException;
public  Patient getPatientInfoById(String p_patientId)throws LAMSException;
public Patient getPatientInfoByDetails(Patient patient)throws LAMSException;
}
