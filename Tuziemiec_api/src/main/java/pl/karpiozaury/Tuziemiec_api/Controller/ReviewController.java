package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Model.Review;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.ReviewRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.ReviewRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

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
    private final ReviewRepository reviewRepository;

    @PostMapping("/add_review")
    public ResponseEntity<?> addNewReview(
            @RequestBody ReviewRequest request,
            Long tripId,
            UsernamePasswordAuthenticationToken token) {
        if(!participationRepository.existsByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId
        )) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nie uczestniczyłeś w takiej wycieczce"));
        }

        if (Period.between(participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId).orElseThrow().getStartDate(), LocalDate.now()).isNegative()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Wycieczka jeszcze się nie odbyła"));
        }

        if (participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId).orElseThrow().getIsReviewed()){
            return ResponseEntity.badRequest().body(new MessageResponse("Dodałeś już komentarz do tej wycieczki"));
        }

        Participation participation = participationRepository.findByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId
        ).orElseThrow();

        Review review = new Review(
                //participationRepository.findByUserIdAndTripId(
                //        userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                //        tripId).orElseThrow(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId,
                request.getCommentHeader(),
                request.getCommentBody(),
                request.getRating()
        );

        participation.setIsReviewed(true);
        participationRepository.save(participation);
        tripService.setRating(tripRepository.findById(tripId).orElseThrow().getTemplate(), (float)request.getRating());
        reviewRepository.save(review);
        return ResponseEntity.ok(new MessageResponse("Dodano komentarz pomyślnie"));
    }

    @GetMapping("/trip_reviews/{id}")
    public ResponseEntity<List<Review>> getTripReviews(@PathVariable("id") Long tripId) {
       List<Review> reviews = reviewRepository.findAllByTripId(tripId).orElseThrow();
       return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user_reviews")
    public ResponseEntity<List<Review>> getUserReviews(UsernamePasswordAuthenticationToken token) {
        List<Review> reviews = reviewRepository.findAllByUserId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}