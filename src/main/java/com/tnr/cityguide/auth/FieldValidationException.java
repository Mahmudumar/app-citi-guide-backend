package com.tnr.cityguide.auth;
import java.util.Map;
public class FieldValidationException extends RuntimeException {
 private final Map<String,String> fieldErrors;
 public FieldValidationException(Map<String,String> errors){this("Please fix the highlighted fields.",errors);}
 public FieldValidationException(String message,Map<String,String> errors){super(message);fieldErrors=errors;}
 public Map<String,String> getFieldErrors(){return fieldErrors;}
}
