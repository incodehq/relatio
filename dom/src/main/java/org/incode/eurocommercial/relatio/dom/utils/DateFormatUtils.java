/**
 * Copyright 2015-2016 Eurocommercial Properties NV
 * <p>
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.incode.eurocommercial.relatio.dom.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DateFormatUtils {
    //illegalargumentexceptions not handled
    public static String toISOLocalDateTime(String dateString, String format) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        final LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return localDateTime.toString();
    }
    //illegalargumentexceptions not handled
    public static String toISOLocalDate(String dateString, String format) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        final LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate.toString();
    }
    public static LocalDate parseStringToLocalDateOrNull(String dateString, String format) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(format);

        try {
            return LocalDate.parse(dateString, formatter);
        }
        catch(Exception e) {
            return null;
        }
    }
}
