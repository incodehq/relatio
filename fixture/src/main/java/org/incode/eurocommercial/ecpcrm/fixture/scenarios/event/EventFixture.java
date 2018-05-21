/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with DemoFixture.this work for additional information
 *  regarding copyright ownership.  The ASF licenses DemoFixture.this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use DemoFixture.this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.incode.eurocommercial.ecpcrm.fixture.scenarios.event;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.eurocommercial.ecpcrm.fixture.dom.event.AccessiEventFixture;
import org.incode.eurocommercial.ecpcrm.fixture.dom.event.ConflictingUtentiEventFixture;
import org.incode.eurocommercial.ecpcrm.fixture.dom.event.UtentiEventFixture;

public class EventFixture extends FixtureScript {
    public EventFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChild(EventFixture.this, new AccessiEventFixture());
        ec.executeChild(EventFixture.this, new UtentiEventFixture());
        ec.executeChild(EventFixture.this, new ConflictingUtentiEventFixture());
    }
}
