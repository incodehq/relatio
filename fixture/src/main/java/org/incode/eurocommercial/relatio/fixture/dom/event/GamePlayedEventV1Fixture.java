package org.incode.eurocommercial.relatio.fixture.dom.event;

import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class GamePlayedEventV1Fixture extends CsvEventFixture {
    public GamePlayedEventV1Fixture() {
        super();
        fileName = "GamePlayedEventV1.csv";
        eventSourceType = EventSourceType.GamePlayedEventV1;
    }
}
