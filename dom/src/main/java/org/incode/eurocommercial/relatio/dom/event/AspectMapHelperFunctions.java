package org.incode.eurocommercial.relatio.dom.event;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

public class AspectMapHelperFunctions {

    public static boolean isValidDate(String pDateString){
        try{
            DateUtils.parseDate(pDateString, "yyyy-MM-dd");
            return true;
        } catch (ParseException e){
            return false;
        }
    }

}
