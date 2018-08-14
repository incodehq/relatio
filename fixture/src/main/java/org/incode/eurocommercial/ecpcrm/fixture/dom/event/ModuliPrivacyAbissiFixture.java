package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

@Accessors(fluent = true)
public class ModuliPrivacyAbissiFixture extends CsvEventFixture {
    public ModuliPrivacyAbissiFixture() {
        super();
        fileName = "moduli_privacy_abissi.csv";
        eventSourceType = EventSourceType.Moduli_Privacy_Abissi_Csv;
    }
}
