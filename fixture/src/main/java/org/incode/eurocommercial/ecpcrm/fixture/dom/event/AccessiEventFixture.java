package org.incode.eurocommercial.ecpcrm.fixture.dom.event;

import java.io.IOException;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;

import org.incode.eurocommercial.ecpcrm.dom.event.EventRepository;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceRepository;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class AccessiEventFixture extends FixtureScript {


    @Override
    protected void execute(final ExecutionContext ec) {
        // when
        final String fileName = "wifi_accessi.csv";
        try {
            final byte[] bytes = Resources.toByteArray(
                    Resources.getResource(AccessiEventFixture.class, fileName));
            final Blob blob = new Blob(fileName, "text/csv", bytes);

            EventSourceType.WifiProjects_Accessi_Csv.parseBlob(blob, eventRepository, eventSourceRepository);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ec.addResult(this, null);
    }

    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
}
