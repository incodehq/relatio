package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class UtentiEventFixture extends CsvEventFixture {
    public UtentiEventFixture() {
        super();
        fileName = "wifi_utenti.csv";
        eventSourceType = EventSourceType.WifiProjects_Utenti_Csv;
    }
}
