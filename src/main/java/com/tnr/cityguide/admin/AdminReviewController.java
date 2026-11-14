package com.tnr.cityguide.admin;
import com.tnr.cityguide.review.ReviewService;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {
 private final ReviewService reviews;
 public AdminReviewController(ReviewService reviews){this.reviews=reviews;}
 @GetMapping public List<ReviewService.ReviewDto> list(){return reviews.allForAdmin();}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id){reviews.delete(id,null,true);return ResponseEntity.noContent().build();}
}