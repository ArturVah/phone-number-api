package am.artur.phonenumberapi.service;

import am.artur.phonenumberapi.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryCallingCodesServiceImplTest {

    @InjectMocks
    private CountryCallingCodesServiceImpl countryCallingCodesService;

    @Mock
    private WebClient webClient;

    @Mock
    private WikipediaProperties wikipediaProperties;

    @Mock
    private HtmlCrawler htmlCrawler;

    @Mock
    private Map<String, Country> callingCodesMap;

    @SuppressWarnings("unchecked")
    @Test
    void givenConfigurations_whenCalled_thenOk() {
        //Test data
        final var uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        final var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        final var url = "http://wikipedia.com";
        final var wikiResponseDto = new WikiResponseDto();
        final var parse = new WikiResponseDto.Response();
        final var pageSection = new WikiResponseDto.PageSection();
        final var prefix = "somePrefix";
        pageSection.setLine(prefix + "someText");
        pageSection.setIndex(2);
        parse.setSections(List.of(pageSection));
        final var text = new WikiResponseDto.Text();
        text.setText("testHtml");
        parse.setText(text);
        wikiResponseDto.setParse(parse);
        //Expectations
        when(wikipediaProperties.getUrl()).thenReturn(url);
        when(wikipediaProperties.getPrefix()).thenReturn(prefix);
        when(wikipediaProperties.getTableUrl()).thenReturn("someUrl");
        when(webClient.get()).thenReturn(uriSpec).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.exchangeToMono(any(Function.class))).thenReturn(Mono.just(wikiResponseDto)).thenReturn(Mono.just(wikiResponseDto));
        doNothing().when(htmlCrawler).parse("testHtml", callingCodesMap);
        //Run test scenario
        countryCallingCodesService.populate();
        //Verify
        verifyNoMoreInteractions(wikipediaProperties, webClient, htmlCrawler);
    }

    @Test
    void givenArmenianPhoneNumber_whenCalled_thenCountryReturned() {
        //Test data
        final var armenia = new Country("Armenia");
        //Expectations
        when(callingCodesMap.get(anyString())).then(invocationOnMock -> {
            final var argument = (String) invocationOnMock.getArgument(0);
            if (argument.equals("+374")) {
                return armenia;
            }
            return null;
        });
        //Run test scenario
        final var result = countryCallingCodesService.resolve("+37494032300");
        //Verify
        verifyNoMoreInteractions(callingCodesMap);
        //Asserts
        assertThat(result).isEqualTo(armenia);
    }

    @Test
    void givenUSANumber_whenCalled_thenCountryReturned() {
        //Test data
        final var usa = new Country("USA");
        //Expectations
        when(callingCodesMap.get(anyString())).then(invocationOnMock -> {
            final var argument = (String) invocationOnMock.getArgument(0);
            if (argument.equals("+1")) {
                return usa;
            }
            return null;
        });
        //Run test scenario
        final var result = countryCallingCodesService.resolve("+168230907230");
        //Verify
        verifyNoMoreInteractions(callingCodesMap);
        //Asserts
        assertThat(result).isEqualTo(usa);
    }

    @Test
    void givenRussianNumber_whenCalled_thenCountryReturned() {
        //Test data
        final var russia = new Country("Russia");
        //Expectations
        when(callingCodesMap.get(anyString())).then(invocationOnMock -> {
            final var argument = (String) invocationOnMock.getArgument(0);
            if (argument.equals("+7")) {
                return russia;
            }
            return null;
        });
        //Run test scenario
        final var result = countryCallingCodesService.resolve("+790823437871");
        //Verify
        verifyNoMoreInteractions(callingCodesMap);
        //Asserts
        assertThat(result).isEqualTo(russia);
    }

    @Test
    void givenNumber_whenCalled_thenCountryReturned() {
        //Test data
        final var someCountry = new Country("someCountry");
        //Expectations
        when(callingCodesMap.get(anyString())).then(invocationOnMock -> {
            final var argument = (String) invocationOnMock.getArgument(0);
            if (argument.equals("+61")) {
                return someCountry;
            }
            return null;
        });
        //Run test scenario
        final var result = countryCallingCodesService.resolve("+613248394892");
        //Verify
        verifyNoMoreInteractions(callingCodesMap);
        //Asserts
        assertThat(result).isEqualTo(someCountry);
    }

    @Test
    void givenSomeNumber_whenCalled_thenCountryReturned() {
        //Test data
        final var someCountry = new Country("someCountry");
        //Expectations
        when(callingCodesMap.get(anyString())).then(invocationOnMock -> {
            final var argument = (String) invocationOnMock.getArgument(0);
            if (argument.equals("+39")) {
                return someCountry;
            }
            return null;
        });
        //Run test scenario
        final var result = countryCallingCodesService.resolve("+391237873341");
        //Verify
        verifyNoMoreInteractions(callingCodesMap);
        //Asserts
        assertThat(result).isEqualTo(someCountry);
    }
}
