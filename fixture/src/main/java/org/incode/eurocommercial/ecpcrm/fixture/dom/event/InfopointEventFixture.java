package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class InfopointEventFixture extends CsvEventFixture {
    public InfopointEventFixture() {
        super();
        fileName = "infopoint.csv";
        eventSourceType = EventSourceType.Infopoint_Csv;
    }
}
