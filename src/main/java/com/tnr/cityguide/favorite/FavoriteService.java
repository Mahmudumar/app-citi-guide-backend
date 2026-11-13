package com.tnr.cityguide.favorite;
import com.tnr.cityguide.common.NotFoundException;import com.tnr.cityguide.place.*;import java.util.*;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
@Service public class FavoriteService {
 private final FavoriteRepository favorites;private final PlaceRepository places;private final PlaceService placeService;
 public FavoriteService(FavoriteRepository f,PlaceRepository p,PlaceService s){favorites=f;places=p;placeService=s;}
 @Transactional(readOnly=true) public List<PlaceService.PlaceDto> list(UUID user){return favorites.findByUserIdOrderByCreatedAtDesc(user).stream().map(f->placeService.getSummary(f.getPlaceId(),user)).toList();}
 @Transactional public void add(UUID user,String place){if(!places.existsById(place))throw new NotFoundException("Place not found.");Favorite.Key key=new Favorite.Key(user,place);if(!favorites.existsById(key)){Favorite f=new Favorite();f.setUserId(user);f.setPlaceId(place);favorites.save(f);}}
 @Transactional public void remove(UUID user,String place){favorites.deleteById(new Favorite.Key(user,place));}
}
