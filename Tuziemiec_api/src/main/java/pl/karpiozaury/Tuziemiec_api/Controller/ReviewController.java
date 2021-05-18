package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.*;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.RatingRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.ReviewRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.*;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

import java.lang.annotation.Retention;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private final ParticipationRepository participationRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final TripService tripService;

    @Autowired
    private final ReviewRatingRepository ratingRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    @PostMapping("/add_review")
    public ResponseEntity<?> addNewReview(
            @RequestBody ReviewRequest request,
            UsernamePasswordAuthenticationToken token) {
        // Czy był

        if(!participationRepository.existsByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                request.getTripId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Nie uczestniczyłeś w takiej wycieczce"));
        }
        // Czy dodał
        if(participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                request.getTripId()
        ).orElseThrow().getIsReviewed()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Dodałeś już komentarz do tej wycieczki"));
        }

        // Czy wycieczka się już odbyła
        if(participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                request.getTripId()
        ).orElseThrow().getStartDate().isAfter(LocalDate.now())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Wycieczka jeszcze się nie odbyła"));
        }

        Review review = new Review(
                participationRepository.findByUserIdAndTripId(
                        userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                        request.getTripId()).orElseThrow(),
                request.getCommentHeader(),
                request.getCommentBody(),
                request.getRating(),
                LocalDate.now(),
                tripRepository.findById(request.getTripId()).orElseThrow().getTemplate().getId()
        );

        tripService.setRating(
                tripRepository.findById(request.getTripId()).orElseThrow().getTemplate(),
                request.getRating());

        participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                request.getTripId()).orElseThrow().setIsReviewed(true);

        reviewRepository.save(review);

        return ResponseEntity.ok(new MessageResponse("Dodano opinie o wycieczce!"));
    }

    @PostMapping("/review/add_rating")
    public ResponseEntity<?> addRating(@RequestBody RatingRequest request,
                                       UsernamePasswordAuthenticationToken token){
        ratingRepository.save(new ReviewRating(
                request.getReview(),
                request.getUserId(),
                request.getRating()
        ));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }


    @GetMapping("/template_reviews/{id}")
    public ResponseEntity<List<Review>> getTemplateReviews(@PathVariable("id") Long templateId){
        List<Review> all = reviewRepository.findAllByTemplateId(templateId).orElseThrow();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
//    @GetMapping("/trip_reviews/{id}")
//    public ResponseEntity<List<Review>> getTripReviews(@PathVariable("id") Long tripId) {
////       List<Review> reviews = reviewRepository.findAllByTripId(tripId).orElseThrow();
////       return new ResponseEntity<>(reviews, HttpStatus.OK);
//        return null;
//    }
//
//    @GetMapping("/user_reviews")
//    public ResponseEntity<List<Review>> getUserReviews(UsernamePasswordAuthenticationToken token) {
////        List<Review> reviews = reviewRepository.findAllByUserId(
////                userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();
////        return new ResponseEntity<>(reviews, HttpStatus.OK);
//        return null;
//    }
}
