package am.artur.phonenumberapi.service;

import am.artur.phonenumberapi.model.Country;

public interface CountryCallingCodesService {

    Country resolve(String code);

}
