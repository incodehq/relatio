package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ContestOnline2017EventFixture extends CsvEventFixture {
    public ContestOnline2017EventFixture() {
        super();
        fileName = "contest_online_2017.csv";
        eventSourceType = EventSourceType.ContestOnline_2017_Csv;
    }
}
