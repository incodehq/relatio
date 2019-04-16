package org.incode.eurocommercial.relatio.module.event.fixture;

import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PtaInfopointEventFixture extends CsvEventFixture {
    public PtaInfopointEventFixture() {
        super();
        fileName = "pta_infopoint.csv";
        eventSourceType = EventSourceType.Infopoint_Csv;
    }
}
