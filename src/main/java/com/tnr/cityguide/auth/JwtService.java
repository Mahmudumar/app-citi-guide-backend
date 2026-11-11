package com.tnr.cityguide.auth;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.tnr.cityguide.config.AppProperties;
import com.tnr.cityguide.user.User;
import java.time.*;
import java.util.*;
import javax.crypto.SecretKey;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;
@Service
public class JwtService {
 private final JwtEncoder encoder;private final AppProperties props;private final Clock clock;
 public JwtService(JwtEncoder encoder,AppProperties props,Clock clock){this.encoder=encoder;this.props=props;this.clock=clock;}
 public String accessToken(User user){
  Instant now=clock.instant();JwtClaimsSet claims=JwtClaimsSet.builder().issuer("cityguide-api").issuedAt(now).expiresAt(now.plus(props.jwt().accessTtl())).subject(user.getId().toString()).claim("email",user.getEmail()).claim("role",user.getRole().name()).build();
  return encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),claims)).getTokenValue();
 }
 public long expiresIn(){return props.jwt().accessTtl().toSeconds();}
}