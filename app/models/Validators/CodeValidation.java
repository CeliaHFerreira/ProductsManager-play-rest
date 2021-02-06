package models.Validators;

import play.data.validation.Constraints;
import play.libs.F;

public class CodeValidation extends Constraints.Validator<String> {
    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<String, Object[]>(
                "El codigo de barras introducido no es correcto", new Object[]{""}); }
    @Override
    public boolean isValid(String value) {
        if (value.length() == 13) {
            return true;
        } else { return false;}
    }
}
