package com.tnr.cityguide.config;
import jakarta.servlet.FilterChain;import jakarta.servlet.ServletException;import jakarta.servlet.http.*;import java.io.IOException;import java.util.UUID;import org.slf4j.*;import org.springframework.core.Ordered;import org.springframework.core.annotation.Order;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;
/** Logs request lifecycle metadata only; never records credentials or request/response bodies. */
@Component @Order(Ordered.HIGHEST_PRECEDENCE+20)
public class HttpRequestLoggingFilter extends OncePerRequestFilter {
 private static final Logger log=LoggerFactory.getLogger(HttpRequestLoggingFilter.class);
 protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException{
  String requestId=UUID.randomUUID().toString();long started=System.nanoTime();MDC.put("requestId",requestId);response.setHeader("X-Request-ID",requestId);
  String query=request.getQueryString();log.info("[HTTP IN] {} {}{} | remote={} | contentType={} | contentLength={}",request.getMethod(),request.getRequestURI(),query==null?"":"?"+query,request.getRemoteAddr(),request.getContentType(),request.getContentLengthLong());
  try{chain.doFilter(request,response);}catch(IOException|ServletException|RuntimeException ex){log.error("[HTTP ERROR] {} {}: {}",request.getMethod(),request.getRequestURI(),ex.getMessage(),ex);throw ex;}finally{
   long ms=(System.nanoTime()-started)/1_000_000;int status=response.getStatus();
   if(status>=500)log.error("[HTTP OUT] {} {} | status={} | durationMs={} ",request.getMethod(),request.getRequestURI(),status,ms);else if(status>=400)log.warn("[HTTP OUT] {} {} | status={} | durationMs={}",request.getMethod(),request.getRequestURI(),status,ms);else log.info("[HTTP OUT] {} {} | status={} | durationMs={}",request.getMethod(),request.getRequestURI(),status,ms);
   MDC.remove("requestId");
  }
 }
}