package com.tnr.cityguide.admin;
import com.tnr.cityguide.review.ReviewService;import io.swagger.v3.oas.annotations.tags.Tag;import java.util.UUID;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin/reviews") @Tag(name="Admin reviews")
public class AdminReviewController {
 private final ReviewService service;public AdminReviewController(ReviewService service){this.service=service;}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id){service.delete(id,null,true);return ResponseEntity.noContent().build();}
}
