
package com.compdev.bo;



import com.compdev.dbutil.BusinessDelegator;
import com.compdev.vo.Appointment;
import com.compdev.vo.Patient;
import com.compdev.exceptions.LAMSException;
import com.compdev.exceptions.DatabaseException;


//Validation import
import com.compdev.util.Validate;
import com.compdev.util.AppointmentHelper;





// Java imports
//import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;


/**
 *
 * @author PraveenAdivi
 */
public class BookAppointment implements IBookAppointment
{

    public boolean checkAvailability(Appointment p_appointment) throws LAMSException
    {
        //Get a logger

        Logger log = Logger.getLogger(this.getClass().getName());
        BasicConfigurator.configure();
        //log.addAppender(new FileAppender());
        log.info("Checking Appointment Availability");
        //Validate format of input field values
        try
        {
            Appointment appt  = null;

            appt =  AppointmentHelper.isApponitmentAvailable(p_appointment); //Validates the input values and verifies availability of the appointment
            if(!appt.getPhlebotomistId().equals("-1"))  // validates input format and values in the Database
            {                
               return true;
            }else
            {
                return false;
            }
        }catch(LAMSException lxe)
        {
            log.error("Error: While checking availability "+lxe.getMessage());
            throw lxe;
        }catch(Exception ex)
        {
            log.error("Unexpected error: while checking availability "+ex.getMessage());
            throw new LAMSException("ERROR: Error while checking availability");
        }

    }//checkAvailability


 /**
  * 
  * @param p_appointment
  * @return
  * @throws com.compdev.exceptions.LAMSException
  * Accepts Appointment details and books an appointment with the required details
  */

     public String makeAppointment(Appointment p_appointment) throws LAMSException
     {
        
// get a logger Object
         Logger log = Logger.getLogger(this.getClass().getName());
         log.info("Booking Appointment");

         String AppointmentNumber;
      //  BookAppointment bookAppointment  = new BookAppointment();
         try {


            Appointment appt  = null;
            appt =  AppointmentHelper.isApponitmentAvailable(p_appointment); //Validates the input values and verifies availability of the appointment
            if(!appt.getPhlebotomistId().equals("-1"))  // validates input format and values in the Database
             {
                try
                {
                   
                    AppointmentNumber = BusinessDelegator.createAppointment(appt);
                    if(AppointmentNumber == null ||AppointmentNumber.equals("") )
                    {
                        throw new LAMSException("ERROR: Error in booking an appointment");
                    }
                }catch(Exception ex)
                {
                          log.info("Error in Creating Appointment: "+ex.getMessage());
                          throw new LAMSException("Error: Unable to book Appointment");
                }
             }else
             {
                  log.info("\nAppointment not available anymore");
                 throw new LAMSException("ERROR: Appointment time is not available");
             }
         }catch(LAMSException lxe)
         {
             throw new LAMSException(lxe.getMessage());
         }catch (Exception ex)
         {
               log.info("Unexpected Error while makingAppointment"+ex.getMessage());
               throw new LAMSException("Error: Unable to book Appointment");
         }
      return AppointmentNumber;
    //  return true;
    }

    public Appointment getNextAppointment(Appointment p_appointment) throws LAMSException
    {
            Logger log = Logger.getLogger(this.getClass().getName());
            log.info("getNextAppointment");

           //Validate the appointment data to ensure valid data is specified

            try{
     
               if(!AppointmentHelper.validateAppointment(p_appointment))
               {
                    throw new LAMSException("Appointment Data not valid");
               }

            }catch(LAMSException lxe)
            {
                      throw new LAMSException(lxe.getMessage());
            }



        while(true)
        {

                 try
                 {
                        String strAppointmentDate = p_appointment.getAppointmentDate().trim();
                        String strAppointmentTime = p_appointment.getAppointmentTime().trim();
                        String[] dateArray=strAppointmentDate.split("-");
                        String[] timeArray=strAppointmentTime.split(":");
                        Calendar cal = new GregorianCalendar(Integer.parseInt(dateArray[0]),
                            Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]),
                            Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]));
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                        cal.add(Calendar.MINUTE, 15);
                        String strNewTime=sdf.format(cal.getTime());
                       if (!Validate.isValidAppointmentTime(strNewTime.substring(0,5)))
                       {

                           p_appointment.setAppointmentTime("08:00");
                           cal.add(Calendar.DATE, 1);
                           String strNewDate = sdfDate.format(cal.getTime());                        
                           p_appointment.setAppointmentDate(strNewDate);
                            continue;
                       }else
                       {
                            p_appointment.setAppointmentTime(strNewTime.substring(0,5));
                          //Verify the appointment Details ( PSC avaialbility ) and assign a phlebotomist if not specified
                        p_appointment=AppointmentHelper.isApponitmentAvailable(p_appointment);
                        if(p_appointment.getPhlebotomistId().equals("-1"))//phleb not available
                        {
                            continue;
                        }else
                        {
                            break;
                        }
 
                       }
               }catch( Exception ex)
               {
                   if(ex.getClass().getName().equals("LAMSEXception"))
              {
                  throw new LAMSException(ex.getMessage());
              }else
              {
                    log.error("ERROR:"+ ex.getMessage());
                    throw new LAMSException("Appointment is not available");
              }

               }               
            }//while

        
    return p_appointment;
    }

    /**
     * @author PraveenAdivi
     * @param p_patientId
     * @return
     * @throws com.compdev.exceptions.LAMSException
     */
    public Patient getPatientInfoById(String p_patientId) throws LAMSException{
     //Get logger
         Logger log = Logger.getLogger(this.getClass().getName());
         log.info("getPatientInfoById");



        try
        {
            if(p_patientId == null)
            {
               throw new LAMSException("ERROR: Invalid PatientId specified");
            }
            if(p_patientId != null && !p_patientId.equals("") && !Validate.isPureInteger(p_patientId) && !BusinessDelegator.isValidPatient(p_patientId))
            {
                throw new LAMSException("ERROR: Invalid PatientId specified");
            }
            return BusinessDelegator.getPatientById(p_patientId);
        }
        catch(DatabaseException e)
        {
            log.error("Error while getting PatientInfo"+e.getMessage());
            throw new LAMSException("ERROR: Patient information not available");
        }
        catch(Exception ex)
        {
          log.error("Error while getting PatientInfo"+ex.getMessage());
          throw new LAMSException("Error while getting PatientInfo");

        }
    }

    /**
     * @author PraveenAdivi
     * @param p_patient
     * @return
     * @throws com.compdev.exceptions.LAMSException
     */
    public Patient getPatientInfoByDetails(Patient p_patient) throws LAMSException
    {
        //Get logger
         Logger log = Logger.getLogger(this.getClass().getName());
         log.info("getPatientInfoById");
          String errorMsg = "";
          
        try
        {
            String patientName = p_patient.getName();
            String patientDOB = p_patient.getDob();

            if(patientName == null || patientDOB == null)
            {
                 throw new LAMSException("ERROR: Invalid Patient Details");
            }
            if(patientName != null && !patientName.equals("") && !Validate.isValidName(patientName))
            {
               throw new LAMSException("Invalid Patient Name");
            }

            if(patientDOB != null && !patientDOB.equals("") && !Validate.isValidDate(patientDOB))
            {
                throw new LAMSException("Invalid Patient DOB");
            }
             return BusinessDelegator.getPatientByDetails(p_patient);
        }
        catch(DatabaseException e)
        {
            
         throw new LAMSException("ERROR: Unable to retrieve patient details");
        }
        catch(Exception ex)
        {
            log.error("Unexpected Error occured "+ex.getMessage());
        throw new LAMSException("ERROR:Unable to retrieve patient details");

        }
    }






}

