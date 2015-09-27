package com.compdev.dbutil;


import java.util.*;
import components.data.*;


/**
 *
 * @author Bryan French
 */
public class TestDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IComponentsData db = new DB();

		  //need to do initial load before doing anything else.  If you don't you'll get an
		  //error message stating such

       db.initialLoad("LAMS");
        System.out.println("All appointments");
        List<Object> objs = db.getData("Appointment", "");
        for (Object obj : objs){
            System.out.println(obj);
            System.out.println("");
        }
        System.out.println("*************");
        System.out.println("Getting some Appointments:");
        objs = db.getData("Appointment", "patientid='210'");
        Patient patient = null;
        Phlebotomist phleb = null;
        PSC psc = null;
        for (Object obj : objs){
            System.out.println(obj);
            patient = ((Appointment)obj).getPatientid();
            phleb = ((Appointment)obj).getPhlebid();
            psc = ((Appointment)obj).getPscid();
        }
        System.out.println("************");
        Appointment newAppt = new Appointment("800",java.sql.Date.valueOf("2009-09-01"),java.sql.Time.valueOf("10:15:00"));
        //extra steps here due to persistence api and join, need to create objects in list
        List<AppointmentLabTest> tests = new ArrayList<AppointmentLabTest>();
        AppointmentLabTest test = new AppointmentLabTest("800","86900","292.9");
        test.setDiagnosis((Diagnosis)db.getData("Diagnosis", "code='292.9'").get(0));
        test.setLabTest((LabTest)db.getData("LabTest","id='86900'").get(0));
        tests.add(test);
        newAppt.setAppointmentLabTestCollection(tests);
        newAppt.setPatientid(patient);
        newAppt.setPhlebid(phleb);
        newAppt.setPscid(psc);

        boolean good = db.addData(newAppt);
        objs = db.getData("Appointment", "");
        for (Object obj : objs){
            System.out.println(obj);
            System.out.println("");
        }

        System.out.println("***********");
        System.out.println("Lab Tests: ");
        objs = db.getData("AppointmentLabTest","");
        for (Object obj : objs){
            System.out.println(obj);
            System.out.println("");
        }
 

/*        db.deleteData("PSC","id='700'");
        System.out.println("Getting all PSC:");
        objs = db.getData("PSC", "");
        for (Object obj : objs){
            System.out.println(obj);
        }
*/

        System.out.println("Getting some Appointments: ");
        objs = db.getData("Appointment", "apptdate>'2004-01-01'");
        for (Object obj : objs){
            System.out.println(obj);
            System.out.println(((Appointment)obj).getAppointmentLabTestCollection());
        }
        System.out.println("");
        Patient newObj = new Patient("260","whatever","123 Main",'y',java.sql.Date.valueOf("1962-12-19"));
        ((Patient)newObj).setPhysician(new Physician("10","Dr. Howard"));
        db.addData(newObj);
        System.out.println("after adding 260");
        objs = db.getData("Patient", "");
        for (Object obj : objs){
            System.out.println(obj);
        }
        newObj.setName("A changed name");
        db.addData(newObj);
        System.out.println("after trying add modified object with same key");
        objs = db.getData("Patient", "");
        for (Object obj : objs){
            System.out.println(obj);
        }
        newObj.setId("270");
        newObj.setName("another new object");
        db.addData(newObj);
        System.out.println("after trying add modified object with new key 150");
        objs = db.getData("Patient", "");
        for (Object obj : objs){
            System.out.println(obj);
        }
        newObj = new Patient("250","whatever","123 Main",'y',java.sql.Date.valueOf("1962-12-19"));
        db.addData(newObj);
        System.out.println("after trying add new object with old key 150");
        objs = db.getData("Patient", "");
        for (Object obj : objs){
            System.out.println(obj);
        } 

        //DAMS .....

  }



}
