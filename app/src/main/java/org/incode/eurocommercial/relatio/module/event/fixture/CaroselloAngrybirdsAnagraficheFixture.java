package org.incode.eurocommercial.relatio.module.event.fixture;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;

@Accessors(fluent = true)
public class CaroselloAngrybirdsAnagraficheFixture extends CsvEventFixture {
    public CaroselloAngrybirdsAnagraficheFixture() {
        super();
        fileName = "carosello_angrybirds_anagrafiche.csv";
        eventSourceType = EventSourceType.Carosello_Angry_Birds_Csv;
    }
}
