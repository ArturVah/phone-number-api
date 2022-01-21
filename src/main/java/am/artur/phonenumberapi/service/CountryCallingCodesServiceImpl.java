package am.artur.phonenumberapi.service;

import am.artur.phonenumberapi.model.Country;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.Assert.*;

@Component
class CountryCallingCodesServiceImpl implements CountryCallingCodesService {

    private final WebClient client;

    private final WikipediaProperties wikipediaProperties;

    private final HtmlCrawler htmlCrawler;

    private final Map<String, Country> callingCodesMap;

    public CountryCallingCodesServiceImpl(final WebClient client, final WikipediaProperties wikipediaProperties, final HtmlCrawler htmlCrawler, final Map<String, Country> callingCodesMap) {
        this.client = client;
        this.wikipediaProperties = wikipediaProperties;
        this.htmlCrawler = htmlCrawler;
        this.callingCodesMap = callingCodesMap;
    }

    @PostConstruct
    void populate() {
        final WebClient.RequestHeadersSpec<?> uri = client.get().uri(wikipediaProperties.getUrl());
        final var wikiResponseDtoMono = uri.exchangeToMono(clientResponse -> clientResponse.bodyToMono(WikiResponseDto.class));
        final WikiResponseDto responseDto = wikiResponseDtoMono.block();
        assert responseDto != null;
        final var index = responseDto.getParse().getSections().stream()
                .filter(pageSection -> pageSection.getLine().contains(wikipediaProperties.getPrefix()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Couldn't retrieve calling codes data")).getIndex();
        final WikiResponseDto wikiResponseDto = client.get().uri(String.format(wikipediaProperties.getTableUrl(), index))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(WikiResponseDto.class)).block();
        assert wikiResponseDto != null;
        htmlCrawler.parse(wikiResponseDto.getParse().getText().getText(), callingCodesMap);
    }


    @Override
    public Country resolve(final String code) {
        hasText(code, "Null or empty was passed as a code");
        final String substring02 = code.substring(0, 2);
        if (code.charAt(1) == '1') {
            return Optional.ofNullable(callingCodesMap.get(code.substring(0, 5))).orElseGet(() -> callingCodesMap.get(substring02));
        }
        final String substring03 = code.substring(0, 3);
        if (code.charAt(1) == '7') {
            return Optional.ofNullable(callingCodesMap.get(substring03)).orElseGet(() -> callingCodesMap.get(substring02));
        }
        final String substring08 = code.substring(0, 8);
        if (substring03.equals("+61")) {
            return Optional.ofNullable(callingCodesMap.get(substring08)).orElseGet(() -> callingCodesMap.get(substring03));
        }

        if (substring03.equals("+39")) {
            return Optional.ofNullable(callingCodesMap.get(substring08)).orElseGet(() -> callingCodesMap.get(substring03));
        }
        return Optional.ofNullable(matchForSevenDigits(code)
                .or(() -> matchForSixDigits(code))
                .or(() -> matchForFourDigits(code))
                .orElseGet(() -> matchForThreeDigits(code)))
                    .orElse(new Country("Unknown Country"));
    }

    private Country matchForThreeDigits(final String code) {
        return callingCodesMap.get(code.substring(0, 3));
    }

    private Optional<Country> matchForFourDigits(final String code) {
        return Optional.ofNullable(callingCodesMap.get(code.substring(0, 4)));
    }

    private Optional<Country> matchForSixDigits(final String code) {
        return Optional.ofNullable(callingCodesMap.get(code.substring(0, 6)));
    }

    private Optional<Country> matchForSevenDigits(final String code) {
        return Optional.ofNullable(callingCodesMap.get(code.substring(0, 7)));
    }
}

