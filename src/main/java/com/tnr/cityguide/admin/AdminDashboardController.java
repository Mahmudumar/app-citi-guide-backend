package com.tnr.cityguide.admin;
import com.tnr.cityguide.city.*;
import com.tnr.cityguide.common.NotFoundException;
import com.tnr.cityguide.place.PlaceRepository;
import com.tnr.cityguide.review.*;
import com.tnr.cityguide.user.*;
import java.time.Instant;
import java.util.*;import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {\n private static final Logger log=LoggerFactory.getLogger(AdminDashboardController.class);
 private final PlaceRepository places;
 private final ReviewRepository reviews;
 private final UserRepository users;
 private final CityRepository cities;
 public AdminDashboardController(PlaceRepository places,ReviewRepository reviews,UserRepository users,CityRepository cities){this.places=places;this.reviews=reviews;this.users=users;this.cities=cities;}

 @GetMapping("/account")
 public AccountDto account(@AuthenticationPrincipal Jwt jwt){log.info("[ADMIN] Account requested");
  UUID id=UUID.fromString(jwt.getSubject());
  User admin=users.findByIdAndRole(id,Role.ADMIN).orElseThrow(()->new NotFoundException("Admin account not found."));
  return new AccountDto(admin.getName(),admin.getEmail(),admin.getAvatarUrl(),admin.getRole().name());
 }
 @GetMapping("/dashboard")
 public DashboardDto dashboard(){log.info("[ADMIN] Dashboard statistics requested");
  List<CityCountDto> cityCounts=cities.findAll(Sort.by(Sort.Order.asc("name"))).stream()
   .map(c->new CityCountDto(c.getId(),c.getName(),places.countByCityId(c.getId())))
   .sorted(Comparator.comparingLong(CityCountDto::listingCount).reversed().thenComparing(CityCountDto::name))
   .toList();
  return new DashboardDto(places.count(),reviews.count(),users.countByRole(Role.USER),cities.count(),cityCounts);
 }
 @GetMapping("/team")
 public List<AdminDto> team(){log.info("[ADMIN] Team listing requested");
  return users.findTop8ByRoleOrderByCreatedAtDesc(Role.ADMIN).stream()
   .map(u->new AdminDto(u.getId(),u.getName(),u.getEmail(),u.getAvatarUrl(),u.getRole().name(),u.getCreatedAt()))
   .toList();
 }
 public record AccountDto(String name,String email,String avatar,String role){}
 public record DashboardDto(long totalPlaces,long totalReviews,long registeredUsers,long totalCities,List<CityCountDto> cities){}
 public record CityCountDto(String id,String name,long listingCount){}
 public record AdminDto(UUID id,String name,String email,String avatar,String role,Instant createdAt){}
}