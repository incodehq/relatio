/**
 *  Copyright 2015-2016 Eurocommercial Properties NV
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.incode.eurocommercial.relatio.app.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;

import org.incode.eurocommercial.relatio.dom.profile.Profile;
import org.incode.eurocommercial.relatio.dom.profile.ProfileRepository;

@ViewModel
public class HomePageViewModel {

    public String title() {
        return "All Profiles";
    }

    @Collection(editing = Editing.DISABLED)
    @CollectionLayout(paged=200)
    @HomePage
    public List<Profile> getCustomers() {
        return profileRepository.listAll();
    }

    @Inject
    ProfileRepository profileRepository;
}
