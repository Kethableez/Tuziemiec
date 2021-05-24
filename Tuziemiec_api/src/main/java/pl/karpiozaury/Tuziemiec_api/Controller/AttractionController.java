package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.AttractionRating;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.AttractionRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UsersAttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Service.TripService;
import pl.karpiozaury.Tuziemiec_api.Service.UsersAttractionService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final UsersAttractionRepository usersAttractionRepository;

    @Autowired
    private final UsersAttractionService usersAttractionService;

    @Autowired
    private final TripService tripService;

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<Attraction> all = attractionRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/all/{place}")
    public ResponseEntity<?> getAllByPlace(@PathVariable("place") String place){
        List<Attraction> all = attractionRepository.findAllByPlace(place).orElseThrow();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        Attraction att = attractionRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(att, HttpStatus.OK);
    }

    @PostMapping("/create_attraction")
    public ResponseEntity<?> createAttraction(@RequestBody AttractionRequest request) {
        if(attractionRepository.existsByNameAndPlace(request.getName(), request.getPlace())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Taka atrakcja już istnieje"));
        }
        else {
            tripService.addAttraction(request);
            return ResponseEntity.ok(new MessageResponse("Atrakcja dodana pomyślnie"));
        }
    }

    @PutMapping("/add_rating/")
    public ResponseEntity<?> addRating(UsernamePasswordAuthenticationToken token, @RequestBody AttractionRating request) {
        Long userId = userRepository.findByUsername(token.getName()).orElseThrow().getId();
        if (usersAttractionRepository.existsByUserIdAndAttractionId(userId, request.getAttractionId())) {

            if (!usersAttractionRepository.findByUserIdAndAttractionId(userId, request.getAttractionId()).getIsReviewed()) {
                usersAttractionService.addRating(token, request.getAttractionId(), request.getRating());
                return ResponseEntity.ok(new MessageResponse("Dodano ocenę atrakcji!"));
            }

            else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Dodałeś już ocenę!"));
        }

        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Nie było Cie tam kurwo"));
    }
}
