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
import java.util.*;

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
        List<Trip> Alltrips = new ArrayList<>();
        List<Trip> trips = new ArrayList<>();

        Random rnd = new Random();

        Alltrips.addAll(recommendationService.gudieTrips(token));

        for(Trip t : recommendationService.ratingTrips(token)) {
            if (!Alltrips.contains(t)) Alltrips.add(t);
        }

        for(Trip t : recommendationService.placeTrips(token)) {
            if (!Alltrips.contains(t)) Alltrips.add(t);
        }
        

        if (Alltrips.size() <= 3) {
            trips = Alltrips;
        }
        else {
            Collections.shuffle(Alltrips);
            for (int i = 0; i < 3; i++) trips.add(Alltrips.get(i));
        }

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

}
