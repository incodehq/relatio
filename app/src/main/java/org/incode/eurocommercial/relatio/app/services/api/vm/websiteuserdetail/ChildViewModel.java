package org.incode.eurocommercial.relatio.app.services.api.vm.websiteuserdetail;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import org.incode.eurocommercial.relatio.app.services.api.ApiService;
import org.incode.eurocommercial.relatio.dom.child.Child;

import lombok.Data;
import static org.incode.eurocommercial.relatio.app.services.api.ApiService.asString;

@Data(staticConstructor = "create")
public class ChildViewModel {
    private final String age;
    private final String birthdate;
    private final String genre;

    public static ChildViewModel fromChild(Child child) {
        int age = Years.yearsBetween(child.getBirthdate(), LocalDate.now()).getYears();
        return ChildViewModel.create(
                ApiService.asString(age),
                ApiService.asString(child.getBirthdate()),
                child.getGender().getValue()
        );
    }
}
