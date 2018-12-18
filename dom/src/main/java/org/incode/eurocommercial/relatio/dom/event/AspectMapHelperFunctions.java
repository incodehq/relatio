package org.incode.eurocommercial.relatio.dom.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AspectMapHelperFunctions {

    public static boolean isValidDate(String pDateString){
        try{
            new SimpleDateFormat("MM/dd/yyyy").parse(pDateString);
            return true;
        } catch (ParseException e){
            return false;
        }
    }
    
}
