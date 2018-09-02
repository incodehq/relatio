package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ConflictingUtentiEventFixture extends CsvEventFixture {
    public ConflictingUtentiEventFixture() {
        super();
        fileName = "wifi_utenti_conflicting.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
