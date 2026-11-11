package com.tnr.cityguide.place;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface PlaceRepository extends JpaRepository<Place,String>, JpaSpecificationExecutor<Place> {
 long countByCityId(String cityId);
}
