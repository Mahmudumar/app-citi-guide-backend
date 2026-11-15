package com.tnr.cityguide.config;
import jakarta.servlet.http.*;import java.io.IOException;import java.time.Instant;import org.springframework.http.MediaType;import org.springframework.security.core.AuthenticationException;import org.springframework.security.web.AuthenticationEntryPoint;import org.springframework.stereotype.Component;
@Component public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
 @Override public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException auth)throws IOException{
  response.setStatus(401);response.setContentType(MediaType.APPLICATION_JSON_VALUE);response.getWriter().write("{\"timestamp\":\""+Instant.now()+"\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Authentication required\",\"path\":\""+request.getRequestURI()+"\"}");
 }
}
