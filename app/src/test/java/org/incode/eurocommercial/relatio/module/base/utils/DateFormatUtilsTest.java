package org.incode.eurocommercial.relatio.module.base.utils;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;

import org.incode.eurocommercial.relatio.module.base.utils.DateFormatUtils;

public class DateFormatUtilsTest {

    @Test
    public void parseStringToLocalDateOrNull_happy() {
        //given
        String dateString = "19/03/18";
        String format = "dd/MM/yy";

        //when
        LocalDate result = DateFormatUtils.parseStringToLocalDateOrNull(dateString, format);

        //then
        Assertions.assertThat(result).isEqualTo(new LocalDate(2018,03,19));
    }

    @Test
    public void parseStringToLocalDateOrNull_sad() {
        //given
        String dateString = "Not a dateString";
        String format = "dd/MM/yy";

        //when
        LocalDate result = DateFormatUtils.parseStringToLocalDateOrNull(dateString, format);

        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void parseStringToLocalDateOrNull_emptystring() {
        //given
        String dateString = "";
        String format = "dd/MM/yy";

        //when
        LocalDate result = DateFormatUtils.parseStringToLocalDateOrNull(dateString, format);

        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void parseStringToLocalDateOrNull_nullargument() {
        //given
        String dateString = null;
        String format = "dd/MM/yy";

        //when
        LocalDate result = DateFormatUtils.parseStringToLocalDateOrNull(dateString, format);

        //then
        Assertions.assertThat(result).isNull();
    }
}