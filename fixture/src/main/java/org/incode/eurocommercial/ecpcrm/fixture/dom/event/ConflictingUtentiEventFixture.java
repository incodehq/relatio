package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ConflictingUtentiEventFixture extends CsvEventFixture {
    public ConflictingUtentiEventFixture() {
        super();
        fileName = "wifi_utenti_conflicting.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
