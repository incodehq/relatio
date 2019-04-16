package org.incode.eurocommercial.relatio.module.event.fixture;

import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class WifiprojectUtentiEventFixture extends CsvEventFixture {
    public WifiprojectUtentiEventFixture() {
        super();
        fileName = "wifi_utenti.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
