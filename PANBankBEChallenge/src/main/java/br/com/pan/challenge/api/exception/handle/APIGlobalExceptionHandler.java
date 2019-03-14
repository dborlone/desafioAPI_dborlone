package br.com.pan.challenge.api.exception.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.pan.challenge.api.exception.model.JsonToJavaParsingException;
import br.com.pan.challenge.api.exception.model.OperationalResult;
import br.com.pan.challenge.api.exception.model.ResourceNotFoundException;
import br.com.pan.challenge.api.exception.model.ServiceStatus;

@ControllerAdvice
public class APIGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger LOG = LoggerFactory.getLogger( APIGlobalExceptionHandler.class );
		
	@Autowired
	private MessageSource messageSource;
	
	// Métodos de utilidade
	private ResponseEntity<Object> handleExceptionInternal(String userMessage, String supportMessage, OperationalResult op, HttpStatus httpStatus, Exception ex, WebRequest request){
		List<ServiceStatus> erros = Arrays.asList(new ServiceStatus(op, userMessage, supportMessage));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), httpStatus, request);
	} 
	
	private String getMessage(String messageProperty) {
		return messageSource.getMessage(messageProperty, null, LocaleContextHolder.getLocale());
	}
	
	private List<ServiceStatus> createErrorList(BindingResult bindingResult) {
		List<ServiceStatus> erros = new ArrayList<>();
		
		for(FieldError fields : bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(fields, LocaleContextHolder.getLocale());
			String supportMessage = fields.toString();			
			erros.add(new ServiceStatus(userMessage, supportMessage)); 
		}
		
		return erros;
	}
	
	//Tratamento exceções customizadas	
	@ExceptionHandler({JsonToJavaParsingException.class})
	public ResponseEntity<Object> handleInvalidPeriodException(JsonToJavaParsingException ex, WebRequest request){
		logException(ex);
		return handleExceptionInternal( getMessage("exception.generic.500.runtime"),
										ExceptionUtils.getRootCauseMessage(ex), 
										OperationalResult.GENERIC_ERROR, 
										HttpStatus.BAD_REQUEST, 
										ex, 
										request);
	}
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<Object> handleInvalidPeriodException(ResourceNotFoundException ex, WebRequest request){
		logException(ex);
		return handleExceptionInternal( getMessage("resource.notFound"),
										ExceptionUtils.getRootCauseMessage(ex), 
										OperationalResult.GENERIC_ERROR, 
										HttpStatus.NOT_FOUND, 
										ex, 
										request);
	}
	
	
	// Tratamento exceções Sprint boot	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(ex);
		return handleExceptionInternal( getMessage("invalid.request"),
										ex.getCause()!=null?ex.getCause().toString():ex.toString(), 
										OperationalResult.GENERIC_ERROR, 
										HttpStatus.BAD_REQUEST, 
										ex, 
										request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(ex);
		List<ServiceStatus> errors = createErrorList(ex.getBindingResult());		
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	//Tratamento de excecao generica	
	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<Object> handleRuntimeExceptionException(RuntimeException ex, WebRequest request){
		logException(ex);
		return handleExceptionInternal( getMessage("exception.generic.500.runtime"),
										ExceptionUtils.getRootCauseMessage(ex), 
										OperationalResult.GENERIC_ERROR, 
										HttpStatus.INTERNAL_SERVER_ERROR, 
										ex, 
										request);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleExceptionException(Exception ex, WebRequest request){
		logException(ex);
		return handleExceptionInternal( getMessage("exception.generic.500.exception"),
										ExceptionUtils.getRootCauseMessage(ex), 
										OperationalResult.GENERIC_ERROR, 
										HttpStatus.INTERNAL_SERVER_ERROR, 
										ex, 
										request);
	}

	private void logException(Exception ex) {
		LOG.error(ex.getMessage(), ex);
	}

}
