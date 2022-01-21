package am.artur.phonenumberapi.service;

import am.artur.phonenumberapi.model.Country;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Component
class HtmlCrawler {

    void parse(final String html,final Map<String, Country> callingCodesMap ) {
            final var document = Jsoup.parse(html);
        final var tr = document.select("table").select("tr");
        final var code = 1;
        final var country = 0;
        final var header = 0;
        tr.remove(header);
        for (final Element row : tr) {
            final var data = row.select("td").eachText();
            final var key = data.get(code);
            final var cleanse = cleanse(key);
            cleanse.forEach(s -> callingCodesMap.put(s, new Country(data.get(country))));
        }
    }

    private List<String> cleanse(final String code) {
        final var trimmedCode = code.replace(" ", "").replace("[notes1]", "").replace("assigned", "");
        return Collections.list(new StringTokenizer(trimmedCode, ",")).stream().map(String.class::cast).collect(Collectors.toList());
    }

}
