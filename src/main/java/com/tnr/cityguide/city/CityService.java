package com.tnr.cityguide.city;
import com.tnr.cityguide.common.NotFoundException;
import com.tnr.cityguide.place.PlaceRepository;
import java.util.List;import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CityService {
 private final CityRepository cities; private final PlaceRepository places;
 public CityService(CityRepository cities,PlaceRepository places){this.cities=cities;this.places=places;}
 @Transactional(readOnly=true) public List<CityDto> all(){log.info("[CITY] Listing requested");List<CityDto> result=cities.findAll().stream().map(this::dto).toList();log.info("[CITY] Listing completed: count={}",result.size());return result;}
 @Transactional(readOnly=true) public CityDto get(String id){log.info("[CITY] Detail requested: cityId={}",id);CityDto result=dto(cities.findById(id).orElseThrow(()->new NotFoundException("City not found.")));log.info("[CITY] Detail returned: cityId={}, placeCount={}",id,result.placeCount());return result;}
 private CityDto dto(City c){return new CityDto(c.getId(),c.getName(),c.getCountry(),c.getLat(),c.getLng(),c.getTimezone(),c.getHue(),c.getBlurb(),c.getDescription(),c.getImageUrl(),places.countByCityId(c.getId()));}
 public record CityDto(String id,String name,String country,double lat,double lng,String timezone,int hue,String blurb,String description,String imageUrl,long placeCount){}
}
