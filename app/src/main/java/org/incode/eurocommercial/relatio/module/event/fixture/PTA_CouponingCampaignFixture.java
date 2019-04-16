package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;


@Accessors(fluent = true)
public class PTA_CouponingCampaignFixture extends CsvEventFixture {
    public PTA_CouponingCampaignFixture() {
        super();
        fileName = "PTA_CouponingCampaign.csv";
        eventSourceType = EventSourceType.PTA_CouponingCampaignData;
    }
}


