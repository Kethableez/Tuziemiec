package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.ParticipationService;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    @Autowired
    private final ParticipationService participationService;

    @Autowired
    private final ParticipationRepository participationRepository;

    @Autowired
    private final TripService tripService;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/user_past")
    public ResponseEntity<List<Participation>> getUserPastParticipationList (UsernamePasswordAuthenticationToken token) {
        List<Participation> userPast = participationService.getUserPast(token);
        return new ResponseEntity<>(userPast, HttpStatus.OK);
    }

    @GetMapping("/user_current")
    public ResponseEntity<List<Participation>> getUserCurrentParticipationList (UsernamePasswordAuthenticationToken token) {
        List<Participation> userCurrent = participationService.getUserCurrent(token);
        return new ResponseEntity<>(userCurrent, HttpStatus.OK);
    }

    @GetMapping("/trip_past/{id}")
    public ResponseEntity<List<Participation>> getTripPastParticipationList (@PathVariable("id") Long tripId) {
        List<Participation> tripPast = participationService.getTripPast(tripId);
        return new ResponseEntity<>(tripPast, HttpStatus.OK);
    }

    @GetMapping("/trip_current/{id}")
    public ResponseEntity<List<Participation>> getTripCurrentParticipationList(@PathVariable("id") Long tripId) {
        List<Participation> tripCurrent = participationService.getTripCurrent(tripId);
        return new ResponseEntity<>(tripCurrent, HttpStatus.OK);
    }

    @PostMapping("/participe/{id}")
    public ResponseEntity<?> singToTrip(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
        if(participationService.isAlreadyParticipate(tripId, token)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Jesteś już zapisany"));
        }

        if (tripService.isBookingNotAvailable(tripRepository.findById(tripId).orElseThrow())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Brak miejsc"));
        }

        participationService.addPatricipant(tripId, token);
        tripService.increaseBooking(tripRepository.findById(tripId).orElseThrow());

        return ResponseEntity.ok(new MessageResponse("Zapisano na wycieczkę!"));
    }

    @DeleteMapping("/unparticipate/{id}")
    public ResponseEntity<?> singOutFromTrip(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
        if(!participationRepository.existsByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId)){
            return ResponseEntity.badRequest().body(new MessageResponse("Nie jesteś zapisany do tej wycieczki"));
        }

        Participation p = participationRepository.findByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId).orElseThrow();
        participationRepository.deleteById(p.getId());
        tripService.decreaseBooking(tripRepository.findById(tripId).orElseThrow());
        return ResponseEntity.ok(new MessageResponse("Wypisano z wycieczki!"));
    }

    @GetMapping("/unreviewed")
    public ResponseEntity<List<Participation>> getAllUnreviewedTrips(UsernamePasswordAuthenticationToken token) {
        List<Participation> unreviewedParticipations = participationRepository.findAllByIsReviewedTrue().orElseThrow();
        return new ResponseEntity<>(unreviewedParticipations, HttpStatus.OK);
    }
}
