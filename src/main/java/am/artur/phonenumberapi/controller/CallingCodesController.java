package am.artur.phonenumberapi.controller;

import am.artur.phonenumberapi.service.CountryCallingCodesService;
import am.artur.phonenumberapi.model.CountryDto;
import am.artur.phonenumberapi.model.PhoneNumberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CallingCodesController {

    private final CountryCallingCodesService countryCallingCodesService;

    public CallingCodesController(final CountryCallingCodesService countryCallingCodesService) {
        this.countryCallingCodesService = countryCallingCodesService;
    }

    @GetMapping(path = "/resolve")
    public ResponseEntity<CountryDto> get(@Valid final PhoneNumberDto phoneNumber) {
        final var country = countryCallingCodesService.resolve(phoneNumber.getNumber());
        return ResponseEntity.ok(new CountryDto(country.getCode()));
    }
}
