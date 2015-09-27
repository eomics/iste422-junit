/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.compdev.util;

//java imports
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;

import com.compdev.dbutil.BusinessDelegator;
import com.compdev.exceptions.LAMSException;
import com.compdev.exceptions.DatabaseException;
import com.compdev.vo.Appointment;
import com.compdev.vo.Phlebotomist;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.log4j.BasicConfigurator;


//import com.compdev.util.Validate;

/**
 *
 
 */
public class AppointmentHelper {

   /**
     *  Validated the input fields for the appointment data.
     */
     public static boolean validateAppointment(Appointment p_appointment) throws LAMSException
     {

        //Get a Logger
       Logger log =  Logger.getLogger(Validate.class.getName());
       log.info("Validating Input Data");

        String strPatientId = p_appointment.getPatientId().trim();
        String strAppointmentNumber = p_appointment.getAppointmentNumber();
        String strAppointmentDate = p_appointment.getAppointmentDate();
        String strAppointmentTime = p_appointment.getAppointmentTime();
        String strPSCCode  = p_appointment.getPatientServiceCenterCode();
        String strPhlebotomistId = p_appointment.getPhlebotomistId();
        String strPhysicianId = p_appointment.getPhysicianId();
        HashMap <String, String> hmTestDetails = p_appointment.getTestDetails();

        String exceptionMsg = "";


        //Validate for Null values
        try
        {
        if(strPatientId == null ||
           strAppointmentDate == null ||
           strAppointmentTime == null ||
           strPSCCode == null ||
           strPhysicianId == null ||
           hmTestDetails == null)
            // do not check for phlebotomistId as it can be null
        {
            log.info("All required input values were not specified");
            //Throw exception here and do not proceed with other validation
            throw new LAMSException("Error: Input does not specify all required values");

             //throw new LAMSException();
        }

        //Validate appointment data format and values
        // Do not change the sequence on conditions inside the If statments.
        if(Validate.isPureInteger(strPatientId))
        {
            if(!BusinessDelegator.isValidPatient(strPatientId))
            {
                    exceptionMsg+= "\nError: Invalid PatientId "+strPatientId;
            }
            
        }else
        {
             exceptionMsg+= "\nError: Invalid PatientId format "+strPatientId;
        }

        if(Validate.isPureInteger(strPSCCode) )
        {
            if(!BusinessDelegator.isValidPSC(strPSCCode))
            {
                exceptionMsg+= "\n Error: Invalid Patient Service Center Code "+strPSCCode;
            }
        }else
        {
             exceptionMsg+= "\n Error: Invalid or Patient Service Center Code format "+strPSCCode;
        }
        if(Validate.isPureInteger(strPhlebotomistId))
        {
            if(!BusinessDelegator.isValidPhelbotomist(strPhlebotomistId))
            {
                exceptionMsg+= "\n Error: Invalid PhlebotomistId "+strPhlebotomistId;
            }
        }else
        {
            exceptionMsg+= "\n Error: Invalid PhlebotomistId format "+strPhlebotomistId;
        }
        if(!Validate.isValidAppointmentDate(strAppointmentDate))
        {
            exceptionMsg+="\n Error: Invalid Date or Date format specified "+strAppointmentDate;
        }
        if(!Validate.isValidAppointmentTime(strAppointmentTime))
        {
            exceptionMsg+="\n Error: Invalid Time or Time Format "+strAppointmentTime;
        }
      // Validation for AppointmentNumber not required to check and make an appointment, this field would be null
        /*
        if(strAppointmentNumber != null && !strAppointmentNumber.equals("") && !Validate.isPureString(strAppointmentNumber))
        {
            exceptionMsg+="\n Error: Invalid Appointment Number or Appointment Number Format  "+strAppointmentNumber;
        } */

        if(!Validate.isPureString(strPhysicianId))
        {
            if(!BusinessDelegator.isValidPhysician(strPhysicianId))
            {
                    exceptionMsg+="\n Error: Invalid PhysicianId "+strPhysicianId;
            }
        }else
        {
                    exceptionMsg+="\n Error: Invalid PhysicianId Format  "+strPhysicianId;
        }

     //Validate TestDetails

        if(hmTestDetails.isEmpty())
        {
            exceptionMsg+="\n Error: No Test Details specified";
        }

        Iterator itTestDetails = hmTestDetails.entrySet().iterator();

        while(itTestDetails.hasNext())
        {
          Map.Entry mapTestDetails ;
           mapTestDetails = (Map.Entry)itTestDetails.next();
           String TestId = ((String)mapTestDetails.getKey()).trim();
           String DSMCode = ((String)mapTestDetails.getValue()).trim();

           if(Validate.isPureInteger(TestId))
           {
              if(!BusinessDelegator.isValidLabTest(TestId))
              {
                  exceptionMsg+="\n Error: Invalid TestId "+TestId ;
              }
           }else
           {
                      exceptionMsg+="\n Error: Invalid TestId Format "+TestId ;
           }

           if(Validate.isValidDXCode(DSMCode))
           {
               if(!BusinessDelegator.isValidDSMCode(DSMCode))
               {
                   exceptionMsg+="\n Error: Invalid DSMCode "+DSMCode ;
               }
           }else
           {
                  exceptionMsg+="\n Error: Invalid DSMCode Format "+DSMCode ;
           }
        }

        //Validate phlebotomist availablility if specified.

        //Check PSC avail
        //Check Phleb avail
        



        }catch(DatabaseException dbx)
        {
            log.info("ERROR: Database Exception: "+dbx.getMessage());
            exceptionMsg +="\n ERROR: Error in validating specified data ";

        }catch (Exception ex)  //catch any other possible exception thrown
        {
            log.info("ERROR: Exception occured in validateAppointment method "+ex.getMessage() );
            exceptionMsg +="\n ERROR: Exception occured ";
        }


        //try -catch
        if(!exceptionMsg.equals(""))  // no exceptions found
        {
            LAMSException lxe = new LAMSException(exceptionMsg);
            //LAMSException lxe = new LAMSException();
            throw lxe;
        }else
        {
         return true;
        }


     }//validateAppointment



     /**
      *
      * Validates if Appointment is available with the specified Phlebotomist
      * 
      */
     public static Appointment isApponitmentAvailable(Appointment p_appointment)throws LAMSException
     {
            Logger log =  Logger.getLogger(Validate.class.getName());
            BasicConfigurator.configure();
         // ValidatAppointment data
           // true
                    //validatePSC availability
                      //  true and pleblId specified
                        // checkphlebAvailability.
                      //  false
                        //getlist of phlebs and loop
                               //checkphelbotomist if found return new appointment object with phlebId set. 
         Appointment retApptObj = new Appointment();
         retApptObj.setPhlebotomistId("-1");
         Boolean retVal = false;
          try
          {
                    AppointmentHelper.validateAppointment(p_appointment);// will throw LAMSException if data not valid
                    if(!BusinessDelegator.isPSCAvailable(p_appointment))
                            { throw new LAMSException("Appointment not available at the specified PSC");}

                    String strPhlebotomistId = p_appointment.getPhlebotomistId();

                    //check if Phlebotomist Id is specified and valid 
                    if(strPhlebotomistId != null && !strPhlebotomistId.equals("") && Validate.isPureInteger(strPhlebotomistId) && BusinessDelegator.isValidPhelbotomist(strPhlebotomistId) )
                    {
                        retVal = AppointmentHelper.isPhlebotomistAvailable(p_appointment);
                    }else if(strPhlebotomistId == null) //phlebotomistId not specified loop through all phlebotomist to get the next available one and book appointment
                    {
                        ArrayList<Phlebotomist> phleblist = BusinessDelegator.getAllPhlebotomists();

                        Iterator<Phlebotomist> it = phleblist.iterator();
                        while(it.hasNext())
                        {
                            Phlebotomist phleb = null;
                            phleb = it.next();
                            p_appointment.setPhlebotomistId(phleb.getPhlebotomistId());
                            if(isPhlebotomistAvailable(p_appointment))
                            {
                                retApptObj = p_appointment;
                                break;
                            }else
                            {
                                retApptObj.setPhlebotomistId("-1");  // to indicate to the calling method that no phlebotomist is available
                            }
                        }//while

                    }else
                    {
                        throw new LAMSException("ERROR: Phlebotomist Id not Valid");
                    }
          }catch(Exception ex)
          {
              if(ex.getClass().getName().equals("LAMSException"))
              {
                  throw new LAMSException(ex.getMessage());
              }else
              {
                    log.error("ERROR:"+ ex.getMessage());
                    throw new LAMSException("Appointment is not available");
              }
          }

         if(retVal) // if true => the passed Appointment time is available
         {
             return p_appointment;
         }else
         {
            return retApptObj;
         }
         
     }


     //dummy till integration is done

//     public static boolean isPhlebotomistAvailable(Appointment p_appointment)
//     {
//         return false;
//     }
//

     /**
 * @author Soumya
 * @param p_appointment
 * @return boolean
 * @throws com.compdev.exceptions.DatabaseException
 */
     public static boolean isPhlebotomistAvailable(Appointment p_appointment) throws LAMSException{
        //Get a Logger
        Logger log =  Logger.getLogger(Validate.class.getName());
        log.info("Checking phlebotomist's availability");
        String exceptionMsg = "";
        boolean retVal = true;
        String appointmentTime = p_appointment.getAppointmentTime().trim();

        try{
            String strAppointmentDate = p_appointment.getAppointmentDate().trim();
            String strAppointmentTime = p_appointment.getAppointmentTime().trim();
            String[] dateArray=strAppointmentDate.split("-");
            String[] timeArray=strAppointmentTime.split(":");
            Calendar cal = new GregorianCalendar(Integer.parseInt(dateArray[0]),
                Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]),
                Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            cal.add(Calendar.MINUTE, -45);

            for(int i=0;i<5;i++){
                cal.add(Calendar.MINUTE, 15);
                String strNewTime=sdf.format(cal.getTime());
                if (!Validate.isValidAppointmentTime(strNewTime.substring(0, 5))){
                    continue;
                }
                else{
                    //does phleb have an appt elsewhere at specified time?
                    p_appointment.setAppointmentTime(strNewTime.substring(0,5));
                    if(BusinessDelegator.isPhlebotomistAvailable(p_appointment))
                    { 
                        retVal = false; //Phelb is at another PSC
                          p_appointment.setAppointmentTime(strNewTime.substring(0,5));
                        break;
                    }
                    else{
                        retVal=true; //Phleb is free
                    }
                }
        }
     }
catch(DatabaseException dbx){
    log.info("ERROR: Database Exception: "+dbx.getMessage());
    exceptionMsg +="\n ERROR: Error in checking phlebotomist availability ";
}
catch(Exception ex){
    log.info("ERROR: Exception occured in isPhlebotomistAvailable method "+ex.getMessage() );
    exceptionMsg +="\n ERROR: Exception occured while checking phlebotomist availability";
}
if(!exceptionMsg.equals(""))  // no exceptions found
{
    LAMSException lxe = new LAMSException(exceptionMsg);
    throw lxe;
}else
{
  p_appointment.setAppointmentTime(appointmentTime.substring(0,5));  //revert the time to the actual Appointment Time
 return retVal;
}
}


}

