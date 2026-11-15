package com.tnr.cityguide.auth;
import com.tnr.cityguide.config.AppProperties;
import org.springframework.context.annotation.Profile;import org.springframework.mail.SimpleMailMessage;import org.springframework.mail.javamail.JavaMailSender;import org.springframework.stereotype.Service;
@Service @Profile("prod")
public class SmtpMailService implements MailService {
 private final JavaMailSender sender;private final AppProperties properties;
 public SmtpMailService(JavaMailSender sender,AppProperties properties){this.sender=sender;this.properties=properties;}
 public void sendPasswordResetCode(String email,String code){SimpleMailMessage message=new SimpleMailMessage();message.setTo(email);message.setFrom(properties.mail().from());message.setSubject("Your City Guide password reset code");message.setText("Your City Guide code is "+code+". It expires in 10 minutes.");sender.send(message);}
}
