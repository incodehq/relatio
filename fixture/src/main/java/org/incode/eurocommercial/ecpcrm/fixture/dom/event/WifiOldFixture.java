package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

@Accessors(fluent = true)
public class WifiOldFixture extends CsvEventFixture {
    public WifiOldFixture() {
        super();
        fileName = "wifi_old.csv";
        eventSourceType = EventSourceType.Wifi_Old_Csv;
    }
}
