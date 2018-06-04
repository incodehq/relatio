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
public abstract class CsvEventFixture extends FixtureScript {

    String fileName;
    EventSourceType eventSourceType;

    @Override
    protected void execute(final ExecutionContext ec) {
        // when
        try {
            final byte[] bytes = Resources.toByteArray(
                    Resources.getResource(CsvEventFixture.class, fileName));
            final Blob blob = new Blob(fileName, "text/csv", bytes);

            eventSourceType.parseBlob(blob, eventRepository, eventSourceRepository);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ec.addResult(this, null);
    }

    @Inject private EventRepository eventRepository;
    @Inject private EventSourceRepository eventSourceRepository;
}
