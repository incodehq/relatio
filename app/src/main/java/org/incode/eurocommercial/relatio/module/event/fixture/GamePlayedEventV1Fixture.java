package org.incode.eurocommercial.relatio.module.event.fixture;

import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class GamePlayedEventV1Fixture extends CsvEventFixture {
    public GamePlayedEventV1Fixture() {
        super();
        fileName = "GamePlayedEventV1.csv";
        eventSourceType = EventSourceType.GamePlayedEventV1;
    }
}
