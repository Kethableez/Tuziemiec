package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Review;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Model.UserRating;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.ReviewRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.*;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private final UserRatingRepository ratingRepository;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final TripService tripService;

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
                0,
                0,
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


    @GetMapping("/template_reviews/{id}")
    public ResponseEntity<List<Review>> getTemplateReviews(@PathVariable("id") Long templateId){
        List<Review> all = reviewRepository.findAllByTemplateId(templateId).orElseThrow();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PutMapping("/like_review/{id}")
    public ResponseEntity<?> likeReview(@PathVariable("id") Long reviewId,
                                        UsernamePasswordAuthenticationToken token){
        if(ratingRepository.existsByUserIdAndReviewId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                reviewId
        )){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Oceniłeś już!"));
        }

        UserRating rating = new UserRating(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                reviewId,
                1
        );
        ratingRepository.save(rating);


        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setUpVote(review.getUpVote() + 1);

        reviewRepository.save(review);
        return ResponseEntity.ok(new MessageResponse("Like!"));
    }

    @PutMapping("/dislike_review/{id}")
    public ResponseEntity<?> dislikeReview(@PathVariable("id") Long reviewId,
                                           UsernamePasswordAuthenticationToken token){
        if(ratingRepository.existsByUserIdAndReviewId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                reviewId
        )){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Oceniłeś już!"));
        }

        UserRating rating = new UserRating(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                reviewId,
                -1
        );
        ratingRepository.save(rating);


        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setDownVote(review.getDownVote() -1 );

        reviewRepository.save(review);
        return ResponseEntity.ok(new MessageResponse("Dislike!"));
    }
}
