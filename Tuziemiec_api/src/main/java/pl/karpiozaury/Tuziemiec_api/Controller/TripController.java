package pl.karpiozaury.Tuziemiec_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.TripRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripRequest tripRequest, UsernamePasswordAuthenticationToken token){

        Trip trip = new Trip(
                tripRequest.getCity(),
                tripRequest.getName(),
                tripRequest.getDescription(),
                tripRequest.getLimit(),
                tripRequest.getTripDate(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId()
        );

        tripRepository.save(trip);
        return ResponseEntity.ok(new MessageResponse("Trip created successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable("id")Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips(){
        List<Trip> all = tripRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Trip>> getAvaliableTrips(){
        List<Trip> avaliable = tripRepository.findByTripDateGreaterThanEqual(LocalDate.now());
        return new ResponseEntity<>(avaliable, HttpStatus.OK);
    }

    @GetMapping("/past")
    public ResponseEntity<List<Trip>> getPastTrips(){
        List<Trip> past = tripRepository.findByTripDateLessThan(LocalDate.now());
        return new ResponseEntity<>(past, HttpStatus.OK);
    }

    @GetMapping("/guide_history")
    public ResponseEntity<List<Trip>> getGuidePastTrips(UsernamePasswordAuthenticationToken token){
        List<Trip> guide_past_trips = tripRepository.findByTripDateLessThanAndGuideId(
                LocalDate.now(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId());
        return new ResponseEntity<>(guide_past_trips, HttpStatus.OK);
    }
}
