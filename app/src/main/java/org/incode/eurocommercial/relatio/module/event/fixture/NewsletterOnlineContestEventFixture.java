package org.incode.eurocommercial.relatio.module.event.fixture;

import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class NewsletterOnlineContestEventFixture extends CsvEventFixture {
    public NewsletterOnlineContestEventFixture() {
        super();
        fileName = "newsletter_online_contest.csv";
        eventSourceType = EventSourceType.Newsletter_Online_Contest_Csv;
    }
}
