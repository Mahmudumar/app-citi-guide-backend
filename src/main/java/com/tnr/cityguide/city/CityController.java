package com.tnr.cityguide.city;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/cities") @Tag(name="Cities")
public class CityController {
 private final CityService service;
 public CityController(CityService service){this.service=service;}
 @GetMapping public List<CityService.CityDto> all(){return service.all();}
 @GetMapping("/{id}") public CityService.CityDto get(@PathVariable String id){return service.get(id);}
}
