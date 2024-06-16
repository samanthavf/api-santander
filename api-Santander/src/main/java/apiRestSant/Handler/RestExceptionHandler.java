package apiRestSant.Handler;

import java.time.LocalDateTime;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import apiRestSant.Exception.BadRequestExceptionDetails;
import apiRestSant.Exception.UserAlreadyExistException;
import apiRestSant.Exception.ValidationExceptionDetails;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handlerBadREequestException(BadRequestException bre){
		return new ResponseEntity<>(
				BadRequestExceptionDetails.builder()
				.title("Bad request excepition, Check a documentação")
				.status(HttpStatus.BAD_REQUEST.value())
				.details(bre.getMessage())
				.develperMessage(bre.getClass().getName())
				.timestamp(LocalDateTime.now())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerUserAlreadyExistsException(UserAlreadyExistException existsException){
		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
				.title("Usuário já existente")
				.status(HttpStatus.BAD_REQUEST.value())
				.details(existsException.getMessage())
				.develperMessage(existsException.getClass().getName())
				.timestamp(LocalDateTime.now())
				.fields("email")
				.fieldsMessage("O email fornecido já está cadastrado")
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerUserAlreadyExistsException(EntityNotFoundException notFoundException){
		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
				.title("Entity não encontrada")
				.status(HttpStatus.NOT_FOUND.value())
				.details(notFoundException.getMessage())
				.develperMessage(notFoundException.getClass().getName())
				.timestamp(LocalDateTime.now())
				.fields("id")
				.fieldsMessage("O ID fornecido não foi encontrado no banco de dados")
				.build(), HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerNoResourceFoundException(NoResourceFoundException noResourceFoundException){
		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
				.title("Recurso Não Encontrado")
				.status(HttpStatus.NOT_FOUND.value())
				.details(noResourceFoundException.getMessage())
				.develperMessage(noResourceFoundException.getClass().getName())
				.timestamp(LocalDateTime.now())
				.fields("resource")
				.fieldsMessage("O recurso solicitado não foi encontrado no servidor")
				.build(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ValidationExceptionDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException readableException){
		String message = readableException.getMostSpecificCause().getMessage();
		
		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
				.title("Erro de Requisição")
				.status(HttpStatus.BAD_REQUEST.value())
				.details(message)
				.develperMessage(readableException.getClass().getName())
				.timestamp(LocalDateTime.now())
				.fields("JSON")
				.fieldsMessage("O JSON enviado é inválido ou malformado")
				.build(), HttpStatus.BAD_REQUEST);
	}
}
