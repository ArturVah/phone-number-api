package am.artur.phonenumberapi.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

@Getter
@Setter
public class ErrorMessage {
    private final HttpStatus httpStatus;
    private final String message;
    private final String fieldName;

    public ErrorMessage(final HttpStatus httpStatus, final String message, final String fieldName) {
        Assert.notNull(httpStatus, "HTTP status must be provided");
        Assert.hasText(message, "Message must be provided");
        this.httpStatus = httpStatus;
        this.message = message;
        this.fieldName = fieldName;
    }
}
