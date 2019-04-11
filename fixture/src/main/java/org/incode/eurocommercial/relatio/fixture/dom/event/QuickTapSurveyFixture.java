package org.incode.eurocommercial.relatio.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

@Accessors(fluent = true)
public class QuickTapSurveyFixture extends CsvEventFixture {
    public QuickTapSurveyFixture() {
        super();
        fileName = "quickTapSurveyTest.csv";
        eventSourceType = EventSourceType.QuickTapSurveyCarosello;
    }
}
