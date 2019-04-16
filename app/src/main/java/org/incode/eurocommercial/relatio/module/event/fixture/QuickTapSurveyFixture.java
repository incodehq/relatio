package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class QuickTapSurveyFixture extends CsvEventFixture {
    public QuickTapSurveyFixture() {
        super();
        fileName = "quickTapSurveyTest.csv";
        eventSourceType = EventSourceType.QuickTapSurveyCarosello;
    }
}
