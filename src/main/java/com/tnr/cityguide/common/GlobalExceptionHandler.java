package com.tnr.cityguide.common;

import com.tnr.cityguide.auth.FieldValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(FieldValidationException.class)
 ResponseEntity<ApiError> fields(FieldValidationException ex,HttpServletRequest req){return build(HttpStatus.BAD_REQUEST,ex.getMessage(),req,ex.getFieldErrors());}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex,HttpServletRequest req){
  Map<String,String> fields=new LinkedHashMap<>();
  ex.getBindingResult().getFieldErrors().forEach(e->fields.putIfAbsent(e.getField().equals("confirmPassword")?"confirm":e.getField(),e.getDefaultMessage()));
  return build(HttpStatus.BAD_REQUEST,"Please fix the highlighted fields.",req,fields);
 }
 @ExceptionHandler(NotFoundException.class)
 ResponseEntity<ApiError> missing(NotFoundException ex,HttpServletRequest req){return build(HttpStatus.NOT_FOUND,ex.getMessage(),req,Map.of());}
 @ExceptionHandler(ResponseStatusException.class)
 ResponseEntity<ApiError> status(ResponseStatusException ex,HttpServletRequest req){HttpStatus s=HttpStatus.valueOf(ex.getStatusCode().value());return build(s,ex.getReason()==null?s.getReasonPhrase():ex.getReason(),req,Map.of());}
 @ExceptionHandler(AccessDeniedException.class)
 ResponseEntity<ApiError> denied(Exception ex,HttpServletRequest req){return build(HttpStatus.FORBIDDEN,"Forbidden",req,Map.of());}
 @ExceptionHandler(AuthenticationException.class)
 ResponseEntity<ApiError> unauth(Exception ex,HttpServletRequest req){return build(HttpStatus.UNAUTHORIZED,"Authentication required",req,Map.of());}
 @ExceptionHandler(Exception.class)
 ResponseEntity<ApiError> other(Exception ex,HttpServletRequest req){return build(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred.",req,Map.of());}
 private ResponseEntity<ApiError> build(HttpStatus s,String message,HttpServletRequest req,Map<String,String> fields){return ResponseEntity.status(s).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),message,req.getRequestURI(),fields.isEmpty()?null:fields));}
}
