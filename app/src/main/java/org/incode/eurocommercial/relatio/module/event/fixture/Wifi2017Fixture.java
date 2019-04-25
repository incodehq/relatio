package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class Wifi2017Fixture extends CsvEventFixture {
    public Wifi2017Fixture() {
        super();
        fileName = "Wifi2017.csv";
        eventSourceType = EventSourceType.WifiData;
    }
}
