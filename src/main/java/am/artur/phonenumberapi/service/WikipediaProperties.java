package am.artur.phonenumberapi.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal.wikipedia")
public class WikipediaProperties {

    private String url;

    private String prefix;

    private String tableUrl;
    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getTableUrl() {
        return tableUrl;
    }

    public void setTableUrl(final String tableUrl) {
        this.tableUrl = tableUrl;
    }
}
