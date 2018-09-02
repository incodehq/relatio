package org.incode.eurocommercial.relatio.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

@Accessors(fluent = true)
public class ModuliPrivacyPressoInfopointFixture extends CsvEventFixture {
    public ModuliPrivacyPressoInfopointFixture() {
        super();
        fileName = "moduli_privacy_presso_infopoint.csv";
        eventSourceType = EventSourceType.Moduli_Privacy_Presso_Infopoint_Csv;
    }
}
