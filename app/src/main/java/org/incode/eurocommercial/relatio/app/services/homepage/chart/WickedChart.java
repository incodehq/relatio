package org.incode.eurocommercial.relatio.app.services.homepage.chart;

import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.Title;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Value;

import java.io.Serializable;

@Value(semanticsProviderClass=WickedChartSemanticsProvider.class)
public class WickedChart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Options options;

    public void setOptions(Options options) {
        this.options = options;
    }

    public WickedChart(Options options) {
        this.options = options;
    }

    public String title() {
        Title title = getOptions().getTitle();
        return title != null? title.getText(): "Wicked Chart";
    }

    @Programmatic
    public Options getOptions() {
        return options;
    }
}