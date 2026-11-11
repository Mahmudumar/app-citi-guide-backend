package com.tnr.cityguide.place;

import com.tnr.cityguide.city.City;
import com.tnr.cityguide.city.CityRepository;
import com.tnr.cityguide.common.*;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceService {
 private final PlaceRepository places; private final CityRepository cities; private final PlaceImageRepository images; private final Clock clock;
 public PlaceService(PlaceRepository places,CityRepository cities,PlaceImageRepository images){this(places,cities,images,Clock.systemUTC());}
 public PlaceService(PlaceRepository places,CityRepository cities,PlaceImageRepository images,Clock clock){this.places=places;this.cities=cities;this.images=images;this.clock=clock;}
 @Transactional(readOnly=true)
 public PageResponse<PlaceDto> list(String city,String category,Double minRating,String q,String sort,int page,int size){
  if(category!=null&&!Set.of("attraction","restaurant","hotel","event").contains(category))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Unknown category.");
  if(!Set.of("rating","reviews","name").contains(sort))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Unknown sort.");
  if(page<0||size<1||size>100)throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Invalid page or size.");
  Sort order=switch(sort){case "reviews"->Sort.by(Sort.Order.desc("reviewCount"),Sort.Order.asc("name"),Sort.Order.asc("id"));case "name"->Sort.by(Sort.Order.asc("name").ignoreCase(),Sort.Order.asc("id"));default->Sort.by(Sort.Order.desc("ratingAvg"),Sort.Order.asc("name"),Sort.Order.asc("id"));};
  Page<Place> result=places.findAll(PlaceSpecifications.filters(city,category,minRating,q),PageRequest.of(page,size,order));
  return new PageResponse<>(result.getContent().stream().map(p->dto(p,null)).toList(),page,size,result.getTotalElements(),result.getTotalPages());
 }
 @Transactional(readOnly=true) public PlaceDto get(String id){Place p=places.findById(id).orElseThrow(()->new NotFoundException("Place not found."));return dto(p,images.findByPlaceIdOrderBySortOrderAsc(id));}
 private PlaceDto dto(Place p,List<PlaceImage> imageList){
  City city=cities.findById(p.getCityId()).orElse(null); Boolean open=city==null?null:OpeningHours.isOpen(p,ZoneId.of(city.getTimezone()),Instant.now(clock));
  String thumbnail=imageList==null?images.findByPlaceIdOrderBySortOrderAsc(p.getId()).stream().findFirst().map(PlaceImage::getUrl).orElse(null):imageList.stream().findFirst().map(PlaceImage::getUrl).orElse(null);
  return new PlaceDto(p.getId(),p.getCityId(),p.getCategory(),p.getName(),p.getDescription(),p.getAddress(),p.getPhone(),p.getWebsite(),p.getLat(),p.getLng(),p.getHoursText(),p.getExtra(),p.getRatingAvg(),p.getReviewCount(),open,false,thumbnail);
 }
 public record PlaceDto(String id,String cityId,String category,String name,String description,String address,String phone,String website,double lat,double lng,String hoursText,String extra,double avgRating,int reviewCount,Boolean isOpenNow,boolean isFavorite,String thumbnailUrl){}
}
