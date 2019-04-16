package org.incode.eurocommercial.relatio.module.event.fixture;

import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ConflictingUtentiEventFixture extends CsvEventFixture {
    public ConflictingUtentiEventFixture() {
        super();
        fileName = "wifi_utenti_conflicting.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
