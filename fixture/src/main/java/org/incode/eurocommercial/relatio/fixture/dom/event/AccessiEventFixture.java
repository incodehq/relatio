package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class AccessiEventFixture extends CsvEventFixture {
    public AccessiEventFixture() {
        super();
        fileName = "wifi_accessi.csv";
        eventSourceType = EventSourceType.WifiProjects_Accessi_Csv;
    }
}
