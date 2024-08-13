package student_api.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedInfoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public DuplicatedInfoException(String message) {
        super(message);
    }
}
