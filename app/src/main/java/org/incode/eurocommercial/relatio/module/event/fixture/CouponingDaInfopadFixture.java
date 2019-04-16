package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class CouponingDaInfopadFixture extends CsvEventFixture {
    public CouponingDaInfopadFixture() {
        super();
        fileName = "couponing_da_infopad.csv";
        eventSourceType = EventSourceType.Couponing_Da_Infopad_Csv;
    }
}
