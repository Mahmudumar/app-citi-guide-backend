package com.tnr.cityguide.admin;
import com.tnr.cityguide.city.*;import com.tnr.cityguide.place.*;import com.tnr.cityguide.storage.StorageService;import com.tnr.cityguide.user.*;
import java.util.*;import org.springframework.http.HttpStatus;import org.springframework.transaction.annotation.Transactional;import org.springframework.transaction.support.TransactionSynchronization;import org.springframework.transaction.support.TransactionSynchronizationManager;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/admin/images")
public class AdminImageMigrationController {
 private final UserRepository users;private final CityRepository cities;private final PlaceImageRepository images;private final StorageService storage;
 public AdminImageMigrationController(UserRepository users,CityRepository cities,PlaceImageRepository images,StorageService storage){this.users=users;this.cities=cities;this.images=images;this.storage=storage;}
 @PostMapping("/migrate-legacy") @Transactional public MigrationResult migrateLegacy(){int checked=0,migrated=0,unavailable=0,failed=0;List<String> uploaded=new ArrayList<>();
  for(User u:users.findAll()){if(!legacy(u.getAvatarUrl()))continue;checked++;try{var result=storage.migrateLegacy(u.getAvatarUrl(),u.getRole()==Role.ADMIN?"profiles/admins":"profiles/users",2L*1024*1024);uploaded.add(result.publicId());u.setAvatarUrl(result.url());u.setAvatarPublicId(result.publicId());users.save(u);migrated++;}catch(ResponseStatusException e){if(e.getStatusCode()==HttpStatus.NOT_FOUND)unavailable++;else failed++;}catch(RuntimeException e){failed++;}}
  for(City c:cities.findAll()){if(!legacy(c.getImageUrl()))continue;checked++;try{var result=storage.migrateLegacy(c.getImageUrl(),"cities/"+c.getId(),8L*1024*1024);uploaded.add(result.publicId());c.setImageUrl(result.url());c.setImagePublicId(result.publicId());cities.save(c);migrated++;}catch(ResponseStatusException e){if(e.getStatusCode()==HttpStatus.NOT_FOUND)unavailable++;else failed++;}catch(RuntimeException e){failed++;}}
  for(PlaceImage image:images.findAll()){if(!legacy(image.getUrl()))continue;checked++;try{var result=storage.migrateLegacy(image.getUrl(),"places/"+image.getPlaceId(),5L*1024*1024);uploaded.add(result.publicId());image.setUrl(result.url());image.setPublicId(result.publicId());images.save(image);migrated++;}catch(ResponseStatusException e){if(e.getStatusCode()==HttpStatus.NOT_FOUND)unavailable++;else failed++;}catch(RuntimeException e){failed++;}}
  if(TransactionSynchronizationManager.isSynchronizationActive())TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){@Override public void afterCompletion(int status){if(status!=STATUS_COMMITTED)uploaded.forEach(storage::delete);}});
  return new MigrationResult(checked,migrated,unavailable,failed);
 }
 private boolean legacy(String url){return url!=null&&url.contains("/uploads/");}
 public record MigrationResult(int checked,int migrated,int unavailable,int failed){}
}