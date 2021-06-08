package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.ParticipationService;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

import java.util.ArrayList;
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

    @GetMapping("/user_past_part")
    public ResponseEntity<List<Participation>> getUserPastParticipationList (UsernamePasswordAuthenticationToken token) {
        List<Participation> userPast = participationService.getUserPast(token);
        return new ResponseEntity<>(userPast, HttpStatus.OK);
    }

    @GetMapping("/user_current")
    public ResponseEntity<List<Participation>> getUserCurrentParticipationList (UsernamePasswordAuthenticationToken token) {
        List<Participation> userCurrent = participationService.getUserCurrent(token);
        return new ResponseEntity<>(userCurrent, HttpStatus.OK);
    }

    @GetMapping("/user_past")
    public ResponseEntity<List<Trip>> getUserPastTrips (UsernamePasswordAuthenticationToken token) {
        List<Participation> userPast = participationService.getUserPast(token);

        List<Trip> past = new ArrayList<>();
        for (Participation p : userPast) {
            past.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }
        return new ResponseEntity<>(past, HttpStatus.OK);
    }

    @GetMapping("/user_incoming")
    public ResponseEntity<List<Trip>> getUserCurrentTrips (UsernamePasswordAuthenticationToken token) {
        List<Participation> userCurrent = participationService.getUserCurrent(token);

        List<Trip> current = new ArrayList<>();
        for (Participation p : userCurrent) {
            current.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }

        return new ResponseEntity<>(current, HttpStatus.OK);
    }

    @GetMapping("/unreviewed_trips")
    public ResponseEntity<List<Trip>> getUnreviewedTrips(UsernamePasswordAuthenticationToken token){
        List<Participation> unreviewedParticipation = participationRepository.findAllByIsReviewedFalseAndUserId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();

        List<Trip> unreviewed = new ArrayList<>();
        for (Participation p : unreviewedParticipation){
            unreviewed.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }

        return new ResponseEntity<>(unreviewed, HttpStatus.OK);
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
        if(tripRepository.findById(tripId).orElseThrow().getTemplate().getGuideId().equals(userRepository.findByUsername(token.getName()).orElseThrow().getId())){
            return ResponseEntity.badRequest().body(new MessageResponse("Stworzyłeś tą wycieczkę!"));
        }

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

    @DeleteMapping("/unparticipe/{id}")
    public ResponseEntity<?> singOutFromTrip(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
        if(!participationRepository.existsByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId)){
            return ResponseEntity.badRequest().body(new MessageResponse("Nie jesteś zapisany do tej wycieczki"));
        }

        Participation p = participationRepository.findByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId).orElseThrow();
        participationRepository.deleteById(p.getId());
        tripService.decreaseBooking(tripRepository.findById(tripId).orElseThrow());
        return ResponseEntity.ok(new MessageResponse("Wypisano z wycieczki!"));
    }

    @GetMapping("/isEnrolled/{id}")
    public boolean isUserEnrolled(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
        return participationRepository.existsByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId
        );
    }
}
