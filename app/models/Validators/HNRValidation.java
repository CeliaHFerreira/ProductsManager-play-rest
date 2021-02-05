package models.Validators;

import play.data.validation.Constraints;
import play.libs.F;

public class HNRValidation extends Constraints.Validator<String> {
    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<String, Object[]>(
                "los productos son para reparación, nutrición o hidratación, en caso de no saberlo debe quedar sin indicar", new Object[]{""}); }
    @Override
    public boolean isValid(String value) {
        if (value == null || value.toLowerCase().equals("hidratación") || value.toLowerCase().equals("nutrición") || value.toLowerCase().equals("reparación")) {
            return true;
        } else { return false;}
    }
}
