package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class WifiprojectUtentiEventFixture extends CsvEventFixture {
    public WifiprojectUtentiEventFixture() {
        super();
        fileName = "wifi_utenti.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
