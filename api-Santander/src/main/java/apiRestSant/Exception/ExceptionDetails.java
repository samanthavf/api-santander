package apiRestSant.Exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {
	protected String title;
	protected int status;
	protected String details;
	protected String develperMessage;
	protected LocalDateTime timestamp;
}
