package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class InfopointEventFixture extends CsvEventFixture {
    public InfopointEventFixture() {
        super();
        fileName = "infopoint.csv";
        eventSourceType = EventSourceType.Infopoint_Csv;
    }
}
