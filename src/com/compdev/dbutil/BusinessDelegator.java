package com.compdev.dbutil;

import com.compdev.exceptions.DatabaseException;
import com.compdev.vo.Appointment;
import com.compdev.vo.PatientServiceCenter;
import com.compdev.vo.Phlebotomist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import components.data.*;

/**
 *
 * @author PraveenAdivi
 */
public class BusinessDelegator {

/**
 * @author PraveenAdivi
 * @param p_patientId
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 *
 *
 */

     static IComponentsData db = new DB();
      
    public static boolean isValidPatient(String p_patientId) throws DatabaseException
    {
try{
       // IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("Patient",  "id='"+p_patientId+"'");
        if(objs!=null && objs.size()!=0)
        {
        return true;

        }

        return false;
}
catch(Exception e)
{

throw new DatabaseException(e.getMessage());
}
    }

    /**
     * @author PraveenAdivi
     * @param p_patientId
     * @return
     * @throws com.compdev.exceptions.DatabaseException
     */
    public static com.compdev.vo.Patient getPatientById(String p_patientId) throws DatabaseException
    {
try{
        //IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("Patient",  "id='"+p_patientId+"'");
        if(objs!=null &&objs.size()>0)
        {

            components.data.Patient dbPatient= (components.data.Patient)objs.get(0);
            com.compdev.vo.Patient patient=new com.compdev.vo.Patient();
            patient.setAddress(dbPatient.getAddress());
            patient.setDob(dbPatient.getDateofbirth().toString());
            boolean insurance=false;
            if(dbPatient.getInsurance()=='Y')
            {

            insurance=true;
            }
            patient.setInsurance(insurance);
            patient.setName(dbPatient.getName());
            patient.setPatientId(dbPatient.getId());
            patient.setPhysicianId(dbPatient.getPhysician().getId());
            return patient;
        }
        return null;
}
catch(Exception e)
{
throw new DatabaseException(e.getMessage());

}


    }

/**
 * @author PraveenAdivi
 * @param p_patient
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
    public static com.compdev.vo.Patient getPatientByDetails(com.compdev.vo.Patient p_patient) throws DatabaseException
    {
try{
       // IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("Patient",  "name='"+p_patient.getName()+"' and DATEOFBIRTH='"+java.sql.Date.valueOf(p_patient.getDob())+"'");
        if(objs!=null &&objs.size()>0)
        {
      
            components.data.Patient dbPatient= (components.data.Patient)objs.get(0);
            System.out.println(dbPatient);

            com.compdev.vo.Patient patient=new com.compdev.vo.Patient();
            patient.setAddress(dbPatient.getAddress());
            patient.setDob(dbPatient.getDateofbirth().toString());
            boolean insurance=false;
            if(dbPatient.getInsurance()=='Y')
            {

            insurance=true;
            }
            patient.setInsurance(insurance);
            patient.setName(dbPatient.getName());
            patient.setPatientId(dbPatient.getId());
            patient.setPhysicianId(dbPatient.getPhysician().getId());
            return patient;
        }
        return null;

}
catch(Exception e)
{
throw new DatabaseException(e.getMessage());

}
    }


/**
 * @author PraveenAdivi
 * @param p_phelbotomist
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isValidPhelbotomist(String p_phelbotomist) throws DatabaseException
    {
try{
       // IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("Phlebotomist", "id='"+p_phelbotomist+"'");
        if(objs!=null && objs.size()!=0)
        {
        return true;

        }

        return false;

}
catch(Exception e)
{
throw new DatabaseException(e.getMessage());
}
    }

/**
 * @author PraveenAdivi
 * @param p_physician
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isValidPhysician(String p_physician) throws DatabaseException
    {
try{
        //IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("Physician", "id='"+p_physician+"'");
        
        if(objs!=null && objs.size()!=0)
        {
        return true;

        }

        return false;

}
catch(Exception e)
{

throw new DatabaseException(e.getMessage());
}
    }
/**
 * @author PraveenAdivi
 * @param p_dsmcode
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isValidDSMCode(String p_dsmcode) throws DatabaseException
    {
try{
       // IComponentsData db = new DB();
      // db.initialLoad("LAMS");
        List<Object> objs =  BusinessDelegator.db.getData("Diagnosis", "code='"+p_dsmcode+"'");
        
        if(objs!=null && objs.size()!=0)
        {
        return true;

        }

        return false;

}
catch(Exception e)
{
throw new DatabaseException(e.getMessage());
}
    }

/**
 * @author PraveenAdivi
 * @param p_PSCCode
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isValidPSC(String p_PSCCode) throws DatabaseException
    {
try{
       // IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        List<Object> objs = BusinessDelegator.db.getData("PSC", "id='"+p_PSCCode+"'");
        
        if(objs!=null && objs.size()!=0)
        {
        return true;

        }

        return false;

}
catch(Exception e)
{

throw new DatabaseException(e.getMessage());
}
    }

/**
 * @author PraveenAdivi
 * @param p_LabTestCode
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isValidLabTest(String p_LabTestCode) throws DatabaseException
    {
try
{
       // IComponentsData db = new DB();
       //db.initialLoad("LAMS");
        String sql = "SELECT * from labtest where id='"+p_LabTestCode+"'";

        ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
 
        if(rs!=null && rs.size()!=0)
        {
        return true;

        }

        return false;

}
catch(Exception e)
{
throw new DatabaseException(e.getMessage());
}
    }

/**
 * @author PraveenAdivi
 * @param p_appointment
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
//private static boolean isAppointmentBooked(Appointment p_appointment) throws DatabaseException
//    {
//    try{
//
//    IComponentsData db = new DB();
//           // db.initialLoad("LAMS");
//
//        String sql = "SELECT * from appointment where PSCID='"+p_appointment.getPatientServiceCenterCode()+"' and APPTDATE='"+java.sql.Date.valueOf(p_appointment.getAppointmentDate())+"' and appttime='"+java.sql.Time.valueOf(p_appointment.getAppointmentTime())+"' and PHLEBID='"+p_appointment.getPhlebotomistId()+"'";
//
//        ArrayList<HashMap<String,String>> rs = db.getDataWithColNames(sql);
//        if(rs!=null &&rs.size()>0)
//        {
//        return true;
//
//        }
//        else
//        {
//
//        return false;
//
//        }
//
//    } catch(Exception e)
//    {
//    throw new DatabaseException(e.getMessage());
//
//    }
//
//    }

/**
 * Checks if PSC is available for give date and time
 */

public static boolean isPSCAvailable(Appointment p_appointment)throws DatabaseException
{
     try{

   // IComponentsData db = new DB();
           // db.initialLoad("LAMS");

        String sql = "SELECT * from appointment where PSCID='"+p_appointment.getPatientServiceCenterCode()+
                     "' and APPTDATE='"+java.sql.Date.valueOf(p_appointment.getAppointmentDate())+
                     "' and appttime='"+java.sql.Time.valueOf(p_appointment.getAppointmentTime().concat(":00"))+
                     "'";// and PHLEBID='"+p_appointment.getPhlebotomistId()+"'";

        ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
        if(rs!=null && rs.size()>0)
        {
        return false;

        }
        else
        {

        return true;

        }

    } catch(Exception e)
    {
    throw new DatabaseException(e.getMessage());

    }
}

/**
 * @author PraveenAdivi
 * @param p_Appointment
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static String createAppointment(Appointment p_Appointment) throws DatabaseException
{

    try{
String appId=BusinessDelegator.getNextAppointmentId();
    components.data.Appointment dbAppointment=new components.data.Appointment(appId,java.sql.Date.valueOf(p_Appointment.getAppointmentDate()),java.sql.Time.valueOf(p_Appointment.getAppointmentTime().concat(":00")));
//IComponentsData db = new DB();
//db.initialLoad("LAMS");



HashMap testMap=p_Appointment.getTestDetails();

        //extra steps here due to persistence api and join, need to create objects in list

        List<AppointmentLabTest> tests = new ArrayList<AppointmentLabTest>();
if(testMap!=null)
{
        for (Iterator it=testMap.keySet().iterator(); it.hasNext(); )
                {
                    String colName = (String)it.next();
                String value = (String) testMap.get(colName);

                AppointmentLabTest test = new AppointmentLabTest(appId,colName,value);


        test.setDiagnosis((components.data.Diagnosis)BusinessDelegator.db.getData("Diagnosis", "code='"+value+"'").get(0));
        test.setLabTest((components.data.LabTest)BusinessDelegator.db.getData("LabTest","id='"+colName+"'").get(0));
        tests.add(test);
                }
        dbAppointment.setAppointmentLabTestCollection(tests);
}
        
        dbAppointment.setPatientid((components.data.Patient)BusinessDelegator.db.getData("Patient","id='"+p_Appointment.getPatientId()+"'").get(0));
        dbAppointment.setPhlebid((components.data.Phlebotomist)BusinessDelegator.db.getData("Phlebotomist","id='"+p_Appointment.getPhlebotomistId()+"'").get(0));

        String sql ="select * from PSC where id='"+p_Appointment.getPatientId()+"'";
            
       dbAppointment.setPscid(BusinessDelegator.getDBPSC(p_Appointment.getPatientServiceCenterCode()));

        boolean good = BusinessDelegator.db.addData(dbAppointment);
if(good)
{
return appId;
}
else
{
return null;
}
    } catch(Exception e)
    {

    throw new DatabaseException(e.getMessage());
    }

}

/**
 * @author PraveenAdivi
 * @param id
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
private static components.data.PSC getDBPSC(String id) throws DatabaseException
{

    try{
//IComponentsData db = new DB();
           // db.initialLoad("LAMS");
            String sql ="select * from PSC where id='"+id+"'";
            ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
            int count=0;

components.data.PSC psc=null;
if(rs!=null)
        for(HashMap<String,String> row: rs)
        {
            //if first time through do the headings


            for (Iterator it=row.keySet().iterator(); it.hasNext(); )
            {

                String colName = (String)it.next();
                String value = row.get(colName);
                if(colName.equals("NAME"))
                {
                 psc=new components.data.PSC();
                 psc.setName(value);
                 //System.out.printf("%s",value);
                count++;
                }
                else
                    if(colName.equals("ID"))
                    {
                    psc.setId(value);
                    //System.out.printf("%s",value);
return psc;
                    }


            }

        }


            //System.out.println("phlebotomist from db: "+psc);
            //System.out.println(rs);
            return null;
    }
    catch(Exception e)
    {

    throw new DatabaseException(e.getMessage());
    }

}



/**
 * @author PraveenAdivi
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
private static String getNextAppointmentId() throws DatabaseException
{

    try{
//IComponentsData db = new DB();
           // db.initialLoad("LAMS");
            String sql ="select id from appointment order by id desc";
            ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
            


if(rs!=null)
        for(HashMap<String,String> row: rs)
        {
            //if first time through do the headings


            for (Iterator it=row.keySet().iterator(); it.hasNext(); )
            {

                String colName = (String)it.next();
                String value = row.get(colName);
                
                int appId=Integer.parseInt(value);
                
                appId=appId+1;
             return ""+appId;

            }

        }


            //System.out.println("phlebotomist from db: "+psc);
            //System.out.println(rs);
            return null;
    }
    catch(Exception e)
    {

    throw new DatabaseException(e.getMessage());
    }

}






/**
 * @author PraveenAdivi
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static ArrayList<Phlebotomist> getAllPhlebotomists() throws DatabaseException
{
    try
    {


  //  IComponentsData db = new DB();
            //db.initialLoad("LAMS");
            String sql ="select * from PHLEBOTOMIST";
            ArrayList phlebList=new ArrayList();
            ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
int count=0;
Phlebotomist phebotomist=null;
        for(HashMap<String,String> row: rs)
        {
            //if first time through do the headings
            

            for (Iterator it=row.keySet().iterator(); it.hasNext(); )
            {

                String colName = (String)it.next();
                String value = row.get(colName);
                if(count%2==0)
                {
                 phebotomist=new Phlebotomist();
                 phebotomist.setName(value);
               //  System.out.printf("%s",value);
                }
                else
                    if(count%2==1)
                    {
                    phebotomist.setPhlebotomistId(value);
                //    System.out.printf("%s",value);
                    phlebList.add(phebotomist);
                    }
                count++;
                
            }

        }
return phlebList;

    }
    catch(Exception e)
    {
        throw new DatabaseException(e.getMessage());
    }
}

/**
 * @author PraveenAdivi
 * @return
 * @throws com.compdev.exceptions.DatabaseException
 */
public static ArrayList<com.compdev.vo.PatientServiceCenter> getAllPSCs() throws DatabaseException
{
    try
    {
   // IComponentsData db = new DB();
            //db.initialLoad("LAMS");
            String sql ="select * from PSC";
            ArrayList pscList=new ArrayList();
            ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
int count=0;
com.compdev.vo.PatientServiceCenter patientServiceCenter=null;
        for(HashMap<String,String> row: rs)
        {
            //if first time through do the headings


            for (Iterator it=row.keySet().iterator(); it.hasNext(); )
            {

                String colName = (String)it.next();
                String value = row.get(colName);
                if(count%2==0)
                {
                 patientServiceCenter=new PatientServiceCenter();
                 patientServiceCenter.setPscName(value);
                // System.out.printf("%s",value);
                }
                else
                    if(count%2==1)
                    {
                    patientServiceCenter.setPscId(value);
                   // System.out.printf("%s",value);
                    pscList.add(patientServiceCenter);
                    }
                count++;

            }

        }
return pscList;
    }
    catch(Exception e)
    {

    throw new DatabaseException(e.getMessage());
    }



}

/**
 * @author Soumya
 * @param p_appointment
 * @return boolean
 * @throws com.compdev.exceptions.DatabaseException
 */
public static boolean isPhlebotomistAvailable(Appointment p_appointment) throws DatabaseException
    {
    try{

  //  IComponentsData db = new DB();
           // db.initialLoad("LAMS");

        String sql = "SELECT * from appointment where APPTDATE='"+java.sql.Date.valueOf(p_appointment.getAppointmentDate())
                +"' and appttime='"+java.sql.Time.valueOf(p_appointment.getAppointmentTime().concat(":00"))
                +"' and PHLEBID='"+p_appointment.getPhlebotomistId()
                +"' and PSCID!='"+p_appointment.getPatientServiceCenterCode()+"'";


        ArrayList<HashMap<String,String>> rs = BusinessDelegator.db.getDataWithColNames(sql);
        if(rs!=null &&rs.size()>0)
        {
             return true;//he's not free
        }
        else
        {

                return false;//he's free

        }

    } catch(Exception e)
    {
    throw new DatabaseException(e.getMessage());

    }

    }

    
public static void main(String args[])
{
try
{
       Appointment testAppt = new Appointment();
        testAppt.setAppointmentDate("2009-05-11");
        testAppt.setAppointmentTime("11:00");
        testAppt.setPatientId("220");
        testAppt.setPatientServiceCenterCode("500");
        testAppt.setPhysicianId("20");
        HashMap hm = new HashMap();
        hm.put("86900", "292.9");
        testAppt.setTestDetails(hm);
    System.out.println(BusinessDelegator.isPSCAvailable(testAppt));
}
catch(Exception e)
{

System.out.println(e.getMessage());
}


}




}



