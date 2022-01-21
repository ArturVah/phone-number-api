package am.artur.phonenumberapi.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

public class Country {

    private final String code;

    public Country(final String code) {
        Assert.hasText(code, "Country code should not be null/empty");
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Country rhs = (Country) obj;
        return new EqualsBuilder()
                .append(this.code, rhs.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(code)
                .toHashCode();
    }
}
