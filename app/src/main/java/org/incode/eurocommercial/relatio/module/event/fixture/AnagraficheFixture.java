package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class AnagraficheFixture extends CsvEventFixture {
    public AnagraficheFixture() {
        super();
        fileName = "anagrafiche.csv";
        eventSourceType = EventSourceType.Anagrafiche_Csv;
    }
}
