package com.tnr.cityguide.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfig {
 @Bean SecretKey jwtSecretKey(AppProperties properties) {
  String secret=properties.jwt().secret();
  if(secret==null||secret.getBytes(StandardCharsets.UTF_8).length<32)throw new IllegalStateException("JWT_SECRET must contain at least 32 bytes");
  return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
 }
 @Bean JwtDecoder jwtDecoder(SecretKey key){return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();}
 @Bean JwtEncoder jwtEncoder(SecretKey key){return new NimbusJwtEncoder(new ImmutableSecret<>(key));}
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(12);}
 @Bean JwtAuthenticationConverter jwtAuthenticationConverter(){JwtGrantedAuthoritiesConverter authorities=new JwtGrantedAuthoritiesConverter();authorities.setAuthoritiesClaimName("role");authorities.setAuthorityPrefix("ROLE_");JwtAuthenticationConverter converter=new JwtAuthenticationConverter();converter.setJwtGrantedAuthoritiesConverter(authorities);return converter;}
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationConverter converter,ApiAuthenticationEntryPoint entryPoint,ApiAccessDeniedHandler deniedHandler)throws Exception{
  return http.csrf(csrf->csrf.disable()).cors(Customizer.withDefaults()).sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .formLogin(form->form.disable()).httpBasic(basic->basic.disable())
   .exceptionHandling(e->e.authenticationEntryPoint(entryPoint).accessDeniedHandler(deniedHandler))
   .authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll().requestMatchers("/api/auth/**","/actuator/health","/uploads/**").permitAll().requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/**").authenticated().anyRequest().permitAll())
   .oauth2ResourceServer(oauth->oauth.jwt(jwt->jwt.jwtAuthenticationConverter(converter))).build();
 }
}
