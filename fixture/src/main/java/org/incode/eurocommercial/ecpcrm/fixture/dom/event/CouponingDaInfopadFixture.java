package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

@Accessors(fluent = true)
public class CouponingDaInfopadFixture extends CsvEventFixture {
    public CouponingDaInfopadFixture() {
        super();
        fileName = "couponing_da_infopad.csv";
        eventSourceType = EventSourceType.Couponing_Da_Infopad_Csv;
    }
}
