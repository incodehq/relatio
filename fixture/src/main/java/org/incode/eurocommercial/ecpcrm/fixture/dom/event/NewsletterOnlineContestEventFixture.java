package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class NewsletterOnlineContestEventFixture extends CsvEventFixture {
    public NewsletterOnlineContestEventFixture() {
        super();
        fileName = "newsletter_online_contest.csv";
        eventSourceType = EventSourceType.Newsletter_Online_Contest_Csv;
    }
}
