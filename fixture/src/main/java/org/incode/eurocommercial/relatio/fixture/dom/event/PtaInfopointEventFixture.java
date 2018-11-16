package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PtaInfopointEventFixture extends CsvEventFixture {
    public PtaInfopointEventFixture() {
        super();
        fileName = "pta_infopoint.csv";
        eventSourceType = EventSourceType.Infopoint_Csv;
    }
}
