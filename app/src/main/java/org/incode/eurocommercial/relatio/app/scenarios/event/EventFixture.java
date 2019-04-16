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

package org.incode.eurocommercial.relatio.app.scenarios.event;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.eurocommercial.relatio.module.event.fixture.AnagraficheFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.AnagraficheGadgetCaroselloFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.CaroselloAngrybirdsAnagraficheFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.ConflictingUtentiEventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.ContestOnline2017EventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.CouponingDaInfopadFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.DatabaseWifiYearFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.GamePlayedEventV1Fixture;
import org.incode.eurocommercial.relatio.module.event.fixture.ModuliPrivacyAbissiFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.ModuliPrivacyPressoInfopointFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.NewsletterOnlineContestEventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.PtaInfopointEventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiOldFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectAccessiEventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectUtentiEventFixture;

public class EventFixture extends FixtureScript {
    public EventFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChild(EventFixture.this, new WifiprojectAccessiEventFixture());
        ec.executeChild(EventFixture.this, new WifiprojectUtentiEventFixture());
        ec.executeChild(EventFixture.this, new ConflictingUtentiEventFixture());
        ec.executeChild(EventFixture.this, new ContestOnline2017EventFixture());
        ec.executeChild(EventFixture.this, new PtaInfopointEventFixture());
        ec.executeChild(EventFixture.this, new NewsletterOnlineContestEventFixture());
        ec.executeChild(EventFixture.this, new DatabaseWifiYearFixture());
        ec.executeChild(EventFixture.this, new ModuliPrivacyPressoInfopointFixture());
        ec.executeChild(EventFixture.this, new ModuliPrivacyAbissiFixture());
        ec.executeChild(EventFixture.this, new CouponingDaInfopadFixture());
        ec.executeChild(EventFixture.this, new CaroselloAngrybirdsAnagraficheFixture());
        ec.executeChild(EventFixture.this, new AnagraficheGadgetCaroselloFixture());
        ec.executeChild(EventFixture.this, new AnagraficheFixture());
        ec.executeChild(EventFixture.this, new WifiOldFixture());
        ec.executeChild(EventFixture.this, new GamePlayedEventV1Fixture());
    }
}
