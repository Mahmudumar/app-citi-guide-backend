package com.tnr.cityguide.place;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlaceImageRepository extends JpaRepository<PlaceImage,UUID> {
 List<PlaceImage> findByPlaceIdOrderBySortOrderAsc(String placeId);
}
