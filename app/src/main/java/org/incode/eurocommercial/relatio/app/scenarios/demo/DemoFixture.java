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

package org.incode.eurocommercial.relatio.app.scenarios.demo;

import org.incode.eurocommercial.relatio.app.scenarios.RelatioFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.AnagraficheFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.CaroselloAngrybirdsAnagraficheFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.GamePlayedEventV1Fixture;
import org.incode.eurocommercial.relatio.module.event.fixture.QuickTapSurveyFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectUtentiEventFixture;

public class DemoFixture extends RelatioFixture {
    public DemoFixture() {
        super();
    }

    @Override protected void execute(final ExecutionContext executionContext) {
        super.execute(executionContext);

        executionContext.executeChildren(
                this,
                new AnagraficheFixture(),
                new CaroselloAngrybirdsAnagraficheFixture(),
                new GamePlayedEventV1Fixture(),
                new QuickTapSurveyFixture(),
                new WifiprojectUtentiEventFixture());

    }
}
