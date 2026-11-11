package com.tnr.cityguide.auth;
import java.util.Map;
public class FieldValidationException extends RuntimeException {
 private final Map<String,String> fieldErrors;
 public FieldValidationException(Map<String,String> errors){super("Please fix the highlighted fields.");fieldErrors=errors;}
 public Map<String,String> getFieldErrors(){return fieldErrors;}
}
