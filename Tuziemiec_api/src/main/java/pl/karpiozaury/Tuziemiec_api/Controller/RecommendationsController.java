package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.RecommendationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/recomendations")
@RequiredArgsConstructor
public class RecommendationsController {

    @Autowired
    private final ParticipationRepository participationRepository;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RecommendationService recommendationService;

    @GetMapping("/")
    public ResponseEntity<List<Trip>> getRecommendations(UsernamePasswordAuthenticationToken token){
        List<Trip> trips = new ArrayList<>();
        int day = LocalDate.now().getDayOfMonth();
        day++;

        if (day % 3 == 0) {
            trips = recommendationService.gudieTrips(token);
        }

        else if (day % 3 == 1) {
            trips = recommendationService.ratingTrips(token);
        }

        else if (day % 3 == 2) {
            trips = recommendationService.placeTrips(token);
        }

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

}
