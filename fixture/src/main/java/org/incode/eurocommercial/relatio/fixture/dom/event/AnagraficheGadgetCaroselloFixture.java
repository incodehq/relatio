package org.incode.eurocommercial.relatio.fixture.dom.event;

import lombok.experimental.Accessors;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;

@Accessors(fluent = true)
public class AnagraficheGadgetCaroselloFixture extends CsvEventFixture {
    public AnagraficheGadgetCaroselloFixture() {
        super();
        fileName = "anagrafiche_gadget_carosello.csv";
        eventSourceType = EventSourceType.Anagrafiche_Gadget_Carosello_Csv;
    }
}
