package org.incode.eurocommercial.relatio.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;


@Accessors(fluent = true)
public class PTA_CouponingCampaignFixture extends CsvEventFixture {
    public PTA_CouponingCampaignFixture() {
        super();
        fileName = "PTA_CouponingCampaign.csv";
        eventSourceType = EventSourceType.PTA_CouponingCampaignData;
    }
}


