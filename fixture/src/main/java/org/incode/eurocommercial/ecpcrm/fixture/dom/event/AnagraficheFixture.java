package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

@Accessors(fluent = true)
public class AnagraficheFixture extends CsvEventFixture {
    public AnagraficheFixture() {
        super();
        fileName = "anagrafiche.csv";
        eventSourceType = EventSourceType.Anagrafiche_Csv;
    }
}
