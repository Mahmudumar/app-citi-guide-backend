package com.tnr.cityguide.city;
import com.tnr.cityguide.common.NotFoundException;
import com.tnr.cityguide.place.PlaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CityService {
 private final CityRepository cities; private final PlaceRepository places;
 public CityService(CityRepository cities,PlaceRepository places){this.cities=cities;this.places=places;}
 @Transactional(readOnly=true) public List<CityDto> all(){return cities.findAll().stream().map(this::dto).toList();}
 @Transactional(readOnly=true) public CityDto get(String id){return dto(cities.findById(id).orElseThrow(()->new NotFoundException("City not found.")));}
 private CityDto dto(City c){return new CityDto(c.getId(),c.getName(),c.getCountry(),c.getLat(),c.getLng(),c.getTimezone(),c.getHue(),c.getBlurb(),c.getDescription(),c.getImageUrl(),places.countByCityId(c.getId()));}
 public record CityDto(String id,String name,String country,double lat,double lng,String timezone,int hue,String blurb,String description,String imageUrl,long placeCount){}
}
