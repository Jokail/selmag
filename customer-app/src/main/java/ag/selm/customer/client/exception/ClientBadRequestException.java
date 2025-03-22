package ag.selm.customer.client.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientBadRequestException extends RuntimeException {

    private final List<String> exception;

    public ClientBadRequestException(Throwable cause, List<String> exception) {
        super(cause);
        this.exception = exception;
    }

}
