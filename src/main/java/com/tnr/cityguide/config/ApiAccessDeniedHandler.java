package com.tnr.cityguide.config;
import jakarta.servlet.http.*;import java.io.IOException;import java.time.Instant;import org.springframework.http.MediaType;import org.springframework.security.access.AccessDeniedException;import org.springframework.security.web.access.AccessDeniedHandler;import org.springframework.stereotype.Component;
@Component public class ApiAccessDeniedHandler implements AccessDeniedHandler {
 @Override public void handle(HttpServletRequest request,HttpServletResponse response,AccessDeniedException ex)throws IOException{
  response.setStatus(403);response.setContentType(MediaType.APPLICATION_JSON_VALUE);response.getWriter().write("{\"timestamp\":\""+Instant.now()+"\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"Forbidden\",\"path\":\""+request.getRequestURI()+"\"}");
 }
}
