package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class Wifi2017LAFixture extends CsvEventFixture {
    public Wifi2017LAFixture() {
        super();
        fileName = "Wifi2017LA.csv";
        eventSourceType = EventSourceType.WifiData2017LastAccess;
    }
}
