package am.artur.phonenumberapi.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Pattern.compile("^\\+(?:[0-9]●?){6,14}[0-9]$").matcher(value).matches();
    }
}
