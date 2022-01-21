package am.artur.phonenumberapi.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class WikiResponseDto {

    private Response parse;

    public Response getParse() {
        return parse;
    }

    public void setParse(final Response parse) {
        this.parse = parse;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private List<PageSection> sections;
        private Text text;
        public List<PageSection> getSections() {
            return sections;
        }

        public void setSections(final List<PageSection> sections) {
            this.sections = sections;
        }

        public Text getText() {
            return text;
        }

        public void setText(final Text text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PageSection {
        private Integer index;
        private String line;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(final Integer index) {
            this.index = index;
        }

        public String getLine() {
            return line;
        }

        public void setLine(final String line) {
            this.line = line;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Text {
        @JsonProperty(value = "*")
        private String text;

        public String getText() {
            return text;
        }

        public void setText(final String text) {
            this.text = text;
        }
    }
}
