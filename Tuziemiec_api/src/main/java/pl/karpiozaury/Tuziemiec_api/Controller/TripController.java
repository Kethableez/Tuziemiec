package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.*;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.*;
import pl.karpiozaury.Tuziemiec_api.Service.ParticipationService;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripController {

    static final int started_booking_places = 0;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private TripTemplateRepository tripTemplateRepository;

    //TODO: existsByName inside tripService
    @PostMapping("/create_template")
    public ResponseEntity<?> createTemplate(@RequestBody TripTemplateRequest request,
                                            UsernamePasswordAuthenticationToken token) {
        if(tripTemplateRepository.existsByName(request.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Taki szablon już istnieje"));
        }

        tripService.addTemplate(request, token);
        return ResponseEntity.ok(new MessageResponse("Szablon dodany pomyślnie"));
    }

    //TODO: existsByNameAndGuideId inside tripService
    @PostMapping("/create_trip")
    public ResponseEntity<?> createTrip(@RequestBody TripRequest request,
                                        UsernamePasswordAuthenticationToken token) {
        //Check if template for specific user exists
        if(!tripTemplateRepository.existsByNameAndGuideId(request.getTemplateName(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Nie posiadasz takiego szablonu"));
        }

        tripService.addTrip(request);

        return ResponseEntity.ok(new MessageResponse("Wycieczka dodana pomyślnie"));
    }
    //Edit details of trip
    @PutMapping("/edit_trip/{id}")
    public ResponseEntity<?> editTrip(@PathVariable("id") Long tripId,
                                      @RequestBody TripDataRequest request,
                                      UsernamePasswordAuthenticationToken token){

        if(!tripTemplateRepository.existsByNameAndGuideId(
                tripRepository.findById(tripId).orElseThrow().getTemplate().getName(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId()
        )){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Nie możesz zmienić parametrów tej wycieczki"));
        }

        Trip trip = tripRepository.findById(tripId).orElseThrow();

        if(request.getStartDate() != null){
            trip.setStartDate(request.getStartDate());
        }
        if(request.getEndDate() != null){
            trip.setEndDate(request.getEndDate());
        }
        if(request.getUserLimit() > 0){
            trip.setUserLimit(request.getUserLimit());
        }
        tripRepository.save(trip);
        return ResponseEntity.ok(new MessageResponse("Pomyślnie zmieniono parametry wycziecki"));
    }

    //Edit details of Template
    @PutMapping("/edit_template/{id}")
    public ResponseEntity<?> editTemplate(@PathVariable("id") Long templateId,
                                          @RequestBody TemplateDataRequest request,
                                          UsernamePasswordAuthenticationToken token) {
         if(!tripTemplateRepository.existsByNameAndGuideId(
                 tripTemplateRepository.findById(templateId).orElseThrow().getName(),
                 userRepository.findByUsername(token.getName()).orElseThrow().getId()
         )) {
             return ResponseEntity
                     .badRequest()
                     .body(new MessageResponse("Nie możesz zmienić parametrów tego szablonu"));
         }
         TripTemplate template = tripTemplateRepository.findById(templateId).orElseThrow();

         if(!request.getPlace().equals("")){
             template.setPlace(request.getPlace());
         }

         if(!request.getDescription().equals("")){
             template.setDescription(request.getDescription());
         }

         if(!request.getAttraction_names().isEmpty()){
             Set<Attraction> attractions = new HashSet<>();
             request.getAttraction_names().forEach(
                     (name) -> attractions.add(attractionRepository.findByName(name).orElseThrow())
             );
             template.setAttractions(attractions);
         }

         tripTemplateRepository.save(template);
         return ResponseEntity.ok(new MessageResponse("Pomyślnie zmieniono parametry wycziecki"));
    }

    // Done
//    @PostMapping("/participe/{id}")
//    public ResponseEntity<?> singToTrip(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
//        if(participationService.isAlreadyParticipate(tripId, token)) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Jesteś już zapisany"));
//        }
//
//        if (tripService.isBookingNotAvailable(tripRepository.findById(tripId).orElseThrow())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Brak miejsc"));
//        }
//
//        participationService.addPatricipant(tripId, token);
//        tripService.increaseBooking(tripRepository.findById(tripId).orElseThrow());
//
//        return ResponseEntity.ok(new MessageResponse("Dodano do wycieczki pomyślnie"));
//    }

//    @DeleteMapping("/unparticipate/{id}")
//    public ResponseEntity<?> singOutFromTrip(@PathVariable("id") Long tripId, UsernamePasswordAuthenticationToken token) {
//        if(!participationRepository.existsByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId)){
//            return ResponseEntity.badRequest().body(new MessageResponse("Nie jesteś zapisany do tej wycieczki"));
//        }
//
//        Participation p = participationRepository.findByUserIdAndTripId(userRepository.findByUsername(token.getName()).orElseThrow().getId(), tripId).orElseThrow();
//        participationRepository.deleteById(p.getId());
//        tripService.decreaseBooking(tripRepository.findById(tripId).orElseThrow());
//        return ResponseEntity.ok(new MessageResponse("Usunięto pomyślnie"));
//    }

    // All necessary GetMappings
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable("id")Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

//    @GetMapping("/all_templates")
//    public ResponseEntity<List<TripTemplate>> getAllTemplates() {
//        //TODO: Wyświetlanie listy tylko dla osoby, która stworzyła szablon
//        List<TripTemplate> all = tripTemplateRepository.findAll();
//        return new ResponseEntity<>(all, HttpStatus.OK);
//    }

    @GetMapping("/templates")
    public ResponseEntity<List<TripTemplate>> getUserTemplates(UsernamePasswordAuthenticationToken token) {
        List<TripTemplate> userAll = tripTemplateRepository.findAllByGuideId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();
        return new ResponseEntity<>(userAll, HttpStatus.OK);
    }

    @GetMapping("/created_trips")
    public ResponseEntity<List<Trip>> getGuideTrips(UsernamePasswordAuthenticationToken token) {
        List<TripTemplate> userTemplates = tripTemplateRepository.findAllByGuideId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();

        List<Trip> guideTrips = new ArrayList<>();
        for (TripTemplate template : userTemplates) {
            guideTrips.addAll(tripRepository.findAllByTemplate_Name(template.getName()).orElseThrow());
        }

        return new ResponseEntity<>(guideTrips, HttpStatus.OK);
    }

    @GetMapping("/all_trips")
    public ResponseEntity<List<Trip>> getAllTrips(){
       List<Trip> all = tripRepository.findAll();
       return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Trip>> getAvaliableTrips(){
        //TODO: Wyświetlenie wycieczek na których użytkownik nie jest zapisany,
        //      Pomijanie tych wycieczek na których użytkownik już jest
        //      UsernamePassword...Token... etc.
        List<Trip> avaliable = tripRepository.findByStartDateGreaterThanEqual(LocalDate.now());
        return new ResponseEntity<>(avaliable, HttpStatus.OK);
    }

    @GetMapping("/past")
    public ResponseEntity<List<Trip>> getPastTrips(){
        List<Trip> past = tripRepository.findByStartDateLessThan(LocalDate.now());
        return new ResponseEntity<>(past, HttpStatus.OK);
    }

}