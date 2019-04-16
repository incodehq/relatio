package org.incode.eurocommercial.relatio.module.event.dom;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import org.incode.eurocommercial.relatio.module.event.dom.AspectMapHelperFunctions;

public class AspectMapHelperFunctionsTest {

    @Test
    public void isValidDate_sad_case(){
        assertThat(AspectMapHelperFunctions.isValidDate("-238656-01-22")).isEqualTo(false);
    }

    @Test
    public void isValidDate_happy_case(){
        assertThat(AspectMapHelperFunctions.isValidDate("1995-01-22")).isEqualTo(true);
    }

}