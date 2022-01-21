package am.artur.phonenumberapi.controller;

import am.artur.phonenumberapi.model.Country;
import am.artur.phonenumberapi.service.CountryCallingCodesService;
import am.artur.phonenumberapi.model.CountryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CallingCodesController.class)
class CallingCodesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryCallingCodesService countryCallingCodesService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void givenValidPhoneNumber_whenCalled_thenOk() throws Exception {

        //Test data
        final var countryDto = new CountryDto("Armenia");
        //Expectations
        when(countryCallingCodesService.resolve("+37494030303")).thenReturn(new Country("Armenia"));
        //Run test scenario
        mockMvc.perform(get("/api/resolve").queryParam("number", "+37494030303")).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(countryDto), true));
        //Verify
        verify(countryCallingCodesService).resolve("+37494030303");
        //Asserts
    }

    @Test
    void givenInvalidPhoneNumber_whenCalled_thenBadRequest() throws Exception {
        //Run test scenario
        mockMvc.perform(get("/api/resolve").queryParam("number", "+A7494030303")).andExpect(status().isBadRequest());
    }
}
