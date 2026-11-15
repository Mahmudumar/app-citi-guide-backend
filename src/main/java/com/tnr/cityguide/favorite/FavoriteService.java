package com.tnr.cityguide.favorite;
import com.tnr.cityguide.common.NotFoundException;import com.tnr.cityguide.place.*;import java.util.*;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
@Service public class FavoriteService {
 private static final Logger log=LoggerFactory.getLogger(FavoriteService.class);
 private final FavoriteRepository favorites;private final PlaceRepository places;private final PlaceService placeService;
 public FavoriteService(FavoriteRepository f,PlaceRepository p,PlaceService s){favorites=f;places=p;placeService=s;}
 @Transactional(readOnly=true) public List<PlaceService.PlaceDto> list(UUID user){log.info("[FAVORITE] List requested: userId={}",user);List<PlaceService.PlaceDto> result=favorites.findByUserIdOrderByCreatedAtDesc(user).stream().map(f->placeService.getSummary(f.getPlaceId(),user)).toList();log.info("[FAVORITE] List completed: userId={}, count={}",user,result.size());return result;}
 @Transactional public void add(UUID user,String place){log.info("[FAVORITE] Add requested: userId={}, placeId={}",user,place);if(!places.existsById(place))throw new NotFoundException("Place not found.");Favorite.Key key=new Favorite.Key(user,place);if(!favorites.existsById(key)){Favorite f=new Favorite();f.setUserId(user);f.setPlaceId(place);favorites.save(f);log.info("[FAVORITE] Added: userId={}, placeId={}",user,place);}else{log.info("[FAVORITE] Already exists: userId={}, placeId={}",user,place);}}
 @Transactional public void remove(UUID user,String place){log.info("[FAVORITE] Remove requested: userId={}, placeId={}",user,place);favorites.deleteById(new Favorite.Key(user,place));log.info("[FAVORITE] Remove completed: userId={}, placeId={}",user,place);}
}
