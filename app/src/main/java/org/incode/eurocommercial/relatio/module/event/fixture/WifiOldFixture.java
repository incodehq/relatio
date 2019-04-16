package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class WifiOldFixture extends CsvEventFixture {
    public WifiOldFixture() {
        super();
        fileName = "wifi_old.csv";
        eventSourceType = EventSourceType.Wifi_Old_Csv;
    }
}
