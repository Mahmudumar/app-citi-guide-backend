package com.tnr.cityguide.auth;
import com.github.benmanes.caffeine.cache.*;
import com.tnr.cityguide.config.AppProperties;
import java.time.*;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
@Service
public class CaffeineLoginAttemptService implements LoginAttemptService {
 private record State(int failures,Instant lockedUntil){}
 private final Cache<String,State> cache=Caffeine.newBuilder().expireAfterWrite(10,TimeUnit.MINUTES).maximumSize(100_000).build();
 private final AppProperties props; private final Clock clock;
 public CaffeineLoginAttemptService(AppProperties props,Clock clock){this.props=props;this.clock=clock;}
 public boolean isLocked(String email){State s=cache.getIfPresent(email);return s!=null&&s.lockedUntil()!=null&&s.lockedUntil().isAfter(clock.instant());}
 public int failed(String email){State old=cache.getIfPresent(email);int failures=old==null?0:old.failures();failures++;if(failures>=props.security().loginMaxAttempts()){cache.put(email,new State(0,clock.instant().plusSeconds(props.security().loginLockSeconds())));return 0;}cache.put(email,new State(failures,null));return failures;}
 public void success(String email){cache.invalidate(email);}
 public int secondsLeft(String email){State s=cache.getIfPresent(email);if(s==null||s.lockedUntil()==null)return 0;return (int)Math.max(0,Duration.between(clock.instant(),s.lockedUntil()).toSeconds()+ (Duration.between(clock.instant(),s.lockedUntil()).toMillis()%1000==0?0:1));}
}
