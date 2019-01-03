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

import org.apache.isis.applib.annotation.*;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.settings.dom.jdo.UserSettingJdo;

import java.util.List;
@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
@DomainObject(objectType = "RelatioUserSettingContributions")
public class RelatioUserSettingContributions {

    //region > settings
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            named = "User Settings",
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            named = "Settings",
            defaultView = "table"
    )
    public List<UserSettingJdo> userSettingsFor(final ApplicationUser applicationUser) {
        return settingsService.listAllSettings(applicationUser.getUsername());
    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private RelatioSettingsService settingsService;

    //endregion

}
