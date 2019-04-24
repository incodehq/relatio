package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class OceanEventFixture extends CsvEventFixture {
    public OceanEventFixture() {
        super();
        fileName = "OceanEvent.csv";
        eventSourceType = EventSourceType.QuickTapSurveyCarosello;
    }
}