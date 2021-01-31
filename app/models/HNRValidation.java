package models;

import play.data.validation.Constraints;
import play.libs.F;

public class HNRValidation extends Constraints.Validator<String> {
    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        System.out.println("aqui");
        return new F.Tuple<String, Object[]>(
                "los productos son para reparación, nutrición o hidratación, en caso de no saberlo debe quedar sin indicar", new Object[]{""}); }
    @Override
    public boolean isValid(String value) {
        System.out.println("aui tsmbine");
        if (value.toLowerCase().equals("hidratación") || value.toLowerCase().equals("nutrición") || value.toLowerCase().equals("reparación") || value == null) {
            return true;
        } else { return false;}
    }
}
