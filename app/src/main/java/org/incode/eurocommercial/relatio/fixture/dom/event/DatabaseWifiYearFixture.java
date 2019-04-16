package org.incode.eurocommercial.relatio.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

@Accessors(fluent = true)
public class DatabaseWifiYearFixture extends CsvEventFixture {
    public DatabaseWifiYearFixture() {
        super();
        fileName = "database_wifi_year.csv";
        eventSourceType = EventSourceType.Database_Wifi_2018_Csv;
    }
}
