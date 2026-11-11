package com.tnr.cityguide.config;
import com.tnr.cityguide.user.*;import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.CommandLineRunner;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Component;
@Component
public class AdminBootstrap implements CommandLineRunner {
 private final UserRepository users;private final PasswordEncoder encoder;
 @Value("${ADMIN_EMAIL:}") private String email;@Value("${ADMIN_PASSWORD:}") private String password;
 public AdminBootstrap(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @Override public void run(String... args){if(email.isBlank()||password.isBlank()||users.existsByRole(Role.ADMIN))return;User user=new User();user.setName("City Guide Admin");user.setEmail(email.trim().toLowerCase(java.util.Locale.ROOT));user.setPasswordHash(encoder.encode(password));user.setRole(Role.ADMIN);users.save(user);}
}
