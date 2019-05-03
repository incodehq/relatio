package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class NewQuickTapSurveyFixture extends CsvEventFixture {
    public NewQuickTapSurveyFixture() {
        super();
        fileName = "NewQuickTapSurvey.csv";
        eventSourceType = EventSourceType.NewQuickTapSurveyCarosello;
    }
}