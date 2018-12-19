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

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.ViewModel;
import static org.apache.isis.applib.annotation.Where.EVERYWHERE;
import org.incode.eurocommercial.relatio.dom.profile.Profile;
import org.incode.eurocommercial.relatio.dom.profile.ProfileRepository;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@ViewModel
public class HomePageViewModel {

    public String title() {
        return "Home Page";
    }

    @Property(hidden=EVERYWHERE)
    public WickedChart getChart() {
        return setChart();
    }
    public WickedChart setChart() {
        Options options = new Options();
        options.setChartOptions(new ChartOptions().setType(SeriesType.PIE));
        options.setTitle(new Title("Marketing consent True/False"));

        options.setxAxis(new Axis().setCategories(Arrays.asList(new String[] { "True", "False"})));


        List<Profile> profiles = profileRepository.listAll();

        Number numberOfProfilesWithMarketingConsentTrue = profiles.stream().filter(profile -> profile.getMarketingConsent() != null && profile.getMarketingConsent().equals(Boolean.TRUE)).count();
        Number numberOfProfilesWithMarketingConsentFalse = profiles.stream().filter(profile -> profile.getMarketingConsent() != null && profile.getMarketingConsent().equals(Boolean.FALSE)).count();

        options.addSeries(new SimpleSeries().setName("Data").setData(Arrays.asList(new Number[] { numberOfProfilesWithMarketingConsentTrue, numberOfProfilesWithMarketingConsentFalse })));

        return new WickedChart(options);
    }

    public WickedChart showChart() {
        return getChart();
    }

    @Inject
    ProfileRepository profileRepository;

}
