package models.Validators;

import play.data.validation.Constraints;
import play.libs.F;

public class HNRValidation extends Constraints.Validator<String> {

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<String, Object[]>(
                "product.hnr.validation", new Object[]{""}); }
    @Override
    public boolean isValid(String value) {
        if (value == null || value.toLowerCase().equals("hidratación") ||
                value.toLowerCase().equals("nutrición") || value.toLowerCase().equals("reparación") ||
                value.toLowerCase().equals("hydration") || value.toLowerCase().equals("nutrition") ||
                value.toLowerCase().equals("repair")) {
            return true;
        } else { return false;}
    }
}
