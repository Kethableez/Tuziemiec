package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.*;
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

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final ParticipationService participationService;

    @Autowired
    private final ParticipationRepository participationRepository;

    @Autowired
    private final TripService tripService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final TripTemplateRepository tripTemplateRepository;

    @Autowired
    private final ImageRepository imageRepository;

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

    @PostMapping("/create_trip")
    public ResponseEntity<?> createTrip(@RequestBody TripRequest request,
                                        UsernamePasswordAuthenticationToken token) {

        if(!tripTemplateRepository.existsByNameAndGuideId(request.getTemplateName(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Nie posiadasz takiego szablonu"));
        }

        tripService.addTrip(request);

        return ResponseEntity.ok(new MessageResponse("Wycieczka dodana pomyślnie"));
    }

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
                     .body(new MessageResponse("Nie możesz zmienić parametrów tego szablonu!"));
         }
         TripTemplate template = tripTemplateRepository.findById(templateId).orElseThrow();


         if(!request.getDescription().equals("")){
             template.setDescription(request.getDescription());
         }

         tripTemplateRepository.save(template);
         return ResponseEntity.ok(new MessageResponse("Pomyślnie zmieniono opis szablonu!"));
    }

    // All necessary GetMappings
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable("id")Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @GetMapping("/getPhotoNames/{templateName}")
    public ResponseEntity<List<String>> getPhotoNames(@PathVariable("templateName") String templateName) {
//        List<TripPhoto> photos =  photoRepository.findAllByTripName(templateName);
        List<Image> images = imageRepository.findAllByFilePurpose(templateName);
        List<String> filenames = new ArrayList<>();

        for (Image image : images) {
            filenames.add(image.getFileName());
        }

        return new ResponseEntity<>(filenames, HttpStatus.OK);
    }


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
       List<Trip> all = tripRepository.findAvaliableTrips();
       return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Trip>> getAvaliableTrips(){
        List<Trip> avaliable = tripService.getAvailableTrips();
        return new ResponseEntity<>(avaliable, HttpStatus.OK);
    }

    @GetMapping("/past")
    public ResponseEntity<List<Trip>> getPastTrips(){
        List<Trip> past = tripRepository.findByStartDateLessThan(LocalDate.now());
        return new ResponseEntity<>(past, HttpStatus.OK);
    }
}