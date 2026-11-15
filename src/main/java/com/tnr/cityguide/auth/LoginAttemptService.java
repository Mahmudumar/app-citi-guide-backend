package com.tnr.cityguide.auth;
public interface LoginAttemptService {
 boolean isLocked(String email);
 int failed(String email);
 void success(String email);
 int secondsLeft(String email);
}
