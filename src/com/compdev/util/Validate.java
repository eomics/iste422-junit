

package com.compdev.util;


/**
 *
 * @author Devavrat Sud
 * // Regular expresssion used to validate were created by Prof. French
 */

/* import com.compdev.vo.*;
import com.compdev.exceptions.LAMSException;
import com.compdev.exceptions.DatabaseException;
import com.compdev.dbutil.BusinessDelegator;
*/
//Java imports
import java.util.regex.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.compdev.exceptions.LAMSException;

public class Validate {


/**
 * Desc : Matches a given string to be purely Alphabetic
 * @param str
 * @return boolean
 */
    public static boolean isPureString(String str)
    {
        Pattern patternStr = Pattern.compile("^[A-Za-z]+$",Pattern.CASE_INSENSITIVE);
        Matcher matched = patternStr.matcher(str);
        return matched.matches();
    }

     public static boolean isValidName(String strName)
    {
        Pattern patternStr = Pattern.compile("^[A-Za-z]*[\\s]*[A-Za-z]*$",Pattern.CASE_INSENSITIVE);
        Matcher matched = patternStr.matcher(strName);
        return matched.matches();
    }


    /**
     * Desc : Matches a given String to be a pure number
     * @param str
     * @return boolean
     */

    public static boolean isPureInteger(String str)
    {
        Pattern patternStr = Pattern.compile("^[0-9]+$",Pattern.CASE_INSENSITIVE);
        Matcher matched = patternStr.matcher(str);
        return matched.matches();
    }

    public static boolean isValidDate(String strDate)
    {
//        Pattern patternStr = Pattern.compile("^[1-9]{4}-[0,1][0,1,2]-[0-3][0-9]$",Pattern.CASE_INSENSITIVE);
//        Matcher matched = patternStr.matcher(str);
//        return matched.matches();


         //Verify the date with system calender (M-Friday)
        Date formatedDate;
        Date currDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
             formatedDate = sdf.parse(strDate);

            if (!sdf.format(formatedDate).equals(strDate) || formatedDate.after(currDate))
            {
                throw new LAMSException("Invalid Date Format or Invalid Date");
            }

        }catch(Exception ex)
        {
           
            return false;
        }

        return true;
    }

   /**
    * Desc: Matched date format to DD/MM/YYYY or  format DD.MM.YYYY or DD-MM-YYYY format
    * @param args
    */

//    public static boolean isValidatDate(String str)
//    {
//
//      //  Pattern pattern = Pattern.compile("(0[1-9]|1[0,1,2])[/](0[1-9]|[1,2][0-9]|3[0,1])[/](19|20)[0-9]{2}",Pattern.CASE_INSENSITIVE);
//
//        Pattern pattern = Pattern.compile("^((((0?[1-9]|[1,2][0-9]|3[0,1])[.,-,/,](0?[1,3,5,7,8]|1[0,2])[.,-,/]" +
//                                            "((1[6-9]|[2-9][0-9])?[0-9]{2}))|((0?[1-9]|([1,2][0-9])|3,0)[.,-,/]" +
//                                            "(0?[1,3,4,5,6,7,8,9]|1[0,1,2])[.,-,/]((1[6-9]|[2-9][0-9])?[0-9]{2}))" +
//                                            "|((0?[1-9]|1[0-9]|2[0-8])[.,-,/]0?2[.,-,/]((1[6-9]|[2-9][0-9])?[0-9]{2}))" +
//                                            "|(2,9[.,-,/]0?2[.,-,/]((1[6-9]|[2-9][0-9])?(0[4,8]|[2,4,6,8][0,4,8]" +
//                                            "|[1,3,5,7,9][2,6])|((1,6|[2,4,6,8][0,4,8]|[3,5,7,9][2,6])00)|00)))" +
//                                            "|(((0[1-9]|[1,2][0-9]|3[0,1])(0,[1,3,5,7,8]|1[0,2])((1[6-9]|[2-9][0-9])?[0-9]{2}))" +
//                                            "|((0[1-9]|[1,2][0-9]|3,0)(0[1,3,4,5,6,7,8,9]|1[0,1,2])((1[6-9]|[2-9][0-9])?[0-9]{2}))" +
//                                            "|((0[1-9]|1[0-9]|2[0-8])0,2((1[6-9]|[2-9][0-9])?[0-9]{2}))|(2,9,0,2((1[6-9]|[2-9][0-9])?(0[4,8]" +
//                                            "|[2,4,6,8][0,4,8]|[1,3,5,7,9][2,6])|((1,6|[2,4,6,8][0,4,8]|[3,5,7,9][2,6])00)|00))))$");
//        Matcher matched = pattern.matcher(str);
//        return matched.matches();
//    }

    /**
     * Verifies if the provided DX code is in correct format. String with numerals only and one period in between
     * @param String DXCode
     * @returns boolean
     */

    public static boolean isValidDXCode(String dxCode)
    {
        Pattern patternStr = Pattern.compile("^[0-9]+[.][0-9]+$",Pattern.CASE_INSENSITIVE);
        Matcher matched = patternStr.matcher(dxCode);
        return matched.matches();
    }



    public static boolean isValidAppointmentTime(String strtime)
    {
       //Verify Time format and time between to be between 9:00am to 5:00pm( 09:00 - 14:59) in 15 mins slots
       
        
        Pattern patternStr = Pattern.compile("^((([0]?[8,9])|[1][0-6]):([0,3][0]|[1,4][5]))$",Pattern.CASE_INSENSITIVE);
        Matcher matched = patternStr.matcher(strtime);
        return matched.matches();
        
    }

    public static boolean isValidAppointmentDate(String strDate)
    {
        //Verify the date with system calender (M-Friday)
        Date formatedDate;
        Date currDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
             formatedDate = sdf.parse(strDate);

            if (!sdf.format(formatedDate).equals(strDate) || formatedDate.before(currDate))
            {
                throw new LAMSException("Invalid Date Format or Invalid Date");
            }
          
        }catch(Exception ex)
        {              
            return false;
        }
      
        return true;
    }
   

  /**  public static boolean isValidTime(String strTime)
    {
        // to be implemented
         return false;
    }
*/
 

    /*
     *  Desc : Test driver for Validation functions , to be commented out once testing is complete
     */

    public static void main(String args[])
    {
       //System.out.println( validate.isPureString("*ABCFD"));
        System.out.println( Validate.isValidAppointmentDate("2010-03-01"));
    }

}

