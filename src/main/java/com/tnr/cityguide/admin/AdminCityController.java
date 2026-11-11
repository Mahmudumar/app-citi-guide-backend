package com.tnr.cityguide.admin;
import com.tnr.cityguide.city.*;import com.tnr.cityguide.common.NotFoundException;import com.tnr.cityguide.place.PlaceRepository;
import io.swagger.v3.oas.annotations.tags.Tag;import java.time.ZoneId;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.transaction.annotation.Transactional;import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/admin/cities") @Tag(name="Admin cities")
public class AdminCityController {
 private final CityRepository cities;private final PlaceRepository places;
 public AdminCityController(CityRepository cities,PlaceRepository places){this.cities=cities;this.places=places;}
 public record CityRequest(String id,String name,String country,double lat,double lng,String timezone,Integer hue,String blurb,String description,String imageUrl){}
 @PostMapping @Transactional public ResponseEntity<CityService.CityDto> create(@RequestBody CityRequest r){if(cities.existsById(r.id()))throw new ResponseStatusException(HttpStatus.CONFLICT,"City already exists.");City c=map(r,new City());cities.save(c);return ResponseEntity.status(201).body(dto(c));}
 @PutMapping("/{id}") @Transactional public CityService.CityDto update(@PathVariable String id,@RequestBody CityRequest r){City c=cities.findById(id).orElseThrow(()->new NotFoundException("City not found."));return dto(cities.save(map(r,c)));}
 @DeleteMapping("/{id}") @Transactional public ResponseEntity<Void> delete(@PathVariable String id){City c=cities.findById(id).orElseThrow(()->new NotFoundException("City not found."));if(places.countByCityId(id)>0)throw new ResponseStatusException(HttpStatus.CONFLICT,"This city still has places. Delete or move them first.");cities.delete(c);return ResponseEntity.noContent().build();}
 private City map(CityRequest r,City c){try{ZoneId.of(r.timezone());}catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid timezone.");}c.setId(r.id());c.setName(r.name());c.setCountry(r.country());c.setLat(r.lat());c.setLng(r.lng());c.setTimezone(r.timezone());c.setHue(r.hue()==null?200:r.hue());c.setBlurb(r.blurb());c.setDescription(r.description());c.setImageUrl(r.imageUrl());return c;}
 private CityService.CityDto dto(City c){return new CityService.CityDto(c.getId(),c.getName(),c.getCountry(),c.getLat(),c.getLng(),c.getTimezone(),c.getHue(),c.getBlurb(),c.getDescription(),c.getImageUrl(),places.countByCityId(c.getId()));}
}
