package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class DatabaseWifiYearFixture extends CsvEventFixture {
    public DatabaseWifiYearFixture() {
        super();
        fileName = "database_wifi_year.csv";
        eventSourceType = EventSourceType.Database_Wifi_2018_Csv;
    }
}
