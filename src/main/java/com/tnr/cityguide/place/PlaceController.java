package com.tnr.cityguide.place;
import com.tnr.cityguide.common.PageResponse;import io.swagger.v3.oas.annotations.tags.Tag;import java.util.UUID;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/places") @Tag(name="Places")
public class PlaceController {
 private final PlaceService service;public PlaceController(PlaceService service){this.service=service;}
 @GetMapping public PageResponse<PlaceService.PlaceDto> list(@RequestParam(required=false)String city,@RequestParam(required=false)String category,@RequestParam(required=false)Double minRating,@RequestParam(required=false)String q,@RequestParam(defaultValue="rating")String sort,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size,@AuthenticationPrincipal Jwt jwt){return service.list(city,category,minRating,q,sort,page,size,UUID.fromString(jwt.getSubject()));}
 @GetMapping("/{id}") public PlaceService.PlaceDetailDto get(@PathVariable String id,@AuthenticationPrincipal Jwt jwt){return service.get(id,UUID.fromString(jwt.getSubject()));}
}
