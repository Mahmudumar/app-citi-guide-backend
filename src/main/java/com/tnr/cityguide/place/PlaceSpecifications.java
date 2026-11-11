package com.tnr.cityguide.place;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;
public final class PlaceSpecifications {
 private PlaceSpecifications(){}
 public static Specification<Place> filters(String city,String category,Double minRating,String query){
  return (root,q,cb)->{
   var predicates=new ArrayList<jakarta.persistence.criteria.Predicate>();
   if(city!=null&&!city.isBlank())predicates.add(cb.equal(root.get("cityId"),city));
   if(category!=null&&!category.isBlank())predicates.add(cb.equal(root.get("category"),category));
   if(minRating!=null)predicates.add(cb.greaterThanOrEqualTo(root.get("ratingAvg"),minRating));
   if(query!=null&&!query.isBlank()){
    for(String token:query.trim().toLowerCase(Locale.ROOT).split("\\s+")){
     String like="%"+token.replace("%","\\%").replace("_","\\_")+"%";
     predicates.add(cb.or(cb.like(cb.lower(root.get("name")),like,'\\'),cb.like(cb.lower(root.get("description")),like,'\\'),cb.like(cb.lower(root.get("address")),like,'\\'),cb.like(cb.lower(root.get("category")),like,'\\'),cb.like(cb.lower(root.get("extra")),like,'\\')));
    }
   }
   return cb.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
  };
 }
}
