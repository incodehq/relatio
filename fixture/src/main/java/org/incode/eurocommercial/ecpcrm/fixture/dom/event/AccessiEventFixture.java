package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class AccessiEventFixture extends CsvEventFixture {
    public AccessiEventFixture() {
        super();
        fileName = "wifi_accessi.csv";
        eventSourceType = EventSourceType.WifiProjects_Accessi_Csv;
    }
}
