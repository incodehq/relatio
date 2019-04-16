/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
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
package org.incode.eurocommercial.relatio.app.services.settings;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;
import org.apache.isis.applib.services.eventbus.EventBusService;

import org.incode.module.settings.SettingsModule;
import org.incode.module.settings.dom.ApplicationSettingsService;
import org.incode.module.settings.dom.UserSettingsService;

/**
 * This service simply permanently hides the menus from the {@link ApplicationSettingsService} and
 * {@link UserSettingsService} implementations.
 *
 * <p>
 *     Instead we expose settings using the {@link RelatioSettingsService} wrapper.
 * </p>
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        menuOrder = "1" // register before any domain services that post events
)
public class HideIsisAddonsSettingsFunctionality extends AbstractSubscriber {

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final SettingsModule.ActionDomainEvent<?> event) {
        if(event.getEventPhase() == AbstractDomainEvent.Phase.HIDE) {
            event.hide();
        }
    }

    //region > injected services
    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion

}
