package com.tnr.cityguide.auth;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.context.annotation.Profile;import org.springframework.stereotype.Service;
@Service @Profile("local")
public class LoggingMailService implements MailService {
 private static final Logger log=LoggerFactory.getLogger(LoggingMailService.class);
 public void sendPasswordResetCode(String email,String code){log.info("Password reset code issued for {}: {}",email,code);}
}
