package org.incode.eurocommercial.relatio.canonical.v1;

import org.apache.isis.applib.services.dto.DtoMappingHelper;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DtoFactoryAbstract {
    protected static XMLGregorianCalendar asXMLGregorianCalendar(final LocalDate date) {
        if (date == null) {
            return null;
        }
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.getYear(),date.getMonthOfYear(), date.getDayOfMonth(), 0,0,0,0,0);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
        //return JodaLocalDateXMLGregorianCalendarAdapter.print(date);
    }

    @Inject
    protected DtoMappingHelper mappingHelper;
}
