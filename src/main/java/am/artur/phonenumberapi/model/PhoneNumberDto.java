package am.artur.phonenumberapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhoneNumberDto {

    @ValidPhoneNumber
    private String number;
}
