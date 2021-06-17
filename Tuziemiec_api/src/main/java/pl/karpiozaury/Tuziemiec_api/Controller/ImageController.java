package pl.karpiozaury.Tuziemiec_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Service.ImageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @GetMapping(value = "/getAvatar/{userId}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> showAvatar(@PathVariable("userId") Long userId) {
        try {
            byte[] image = imageService.getImage(userId);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    @GetMapping(value = "/getBackground",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> getDefault() {
        try {
            byte[] image = imageService.getBackground();
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    @GetMapping(value = "/getAttractionPhoto",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> getTripPhoto(@RequestParam("attractionId") Long attractionId) {
        try {
            byte[] image = imageService.getAttractionPhoto(attractionId);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    @GetMapping(value = "/getTripPhoto",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> getTripPhoto(@RequestParam("tripName") String tripName, @RequestParam("fileName") String Filename) {
        try {
            byte[] image = imageService.getTripPhoto(tripName, Filename);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @PostMapping("/uploadAvatar")
    public ResponseEntity<?> uploadAvatarImage(@RequestParam("imageFile") MultipartFile imageFile, UsernamePasswordAuthenticationToken token) {
        try {
            imageService.saveAvatar(imageFile, token);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }

    @PostMapping("/uploadTripPhoto")
    public ResponseEntity<?> uploadTripPhoto(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("templateName") String templateName) {
        try {
            imageService.saveTripPhoto(imageFile, templateName);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }

    @PostMapping("/uploadAttractionPhoto")
    public ResponseEntity<?> uploadAttractionPhoto(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            imageService.saveAttractionPhoto(imageFile);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }

    @PostMapping("/uploadDefault")
    public ResponseEntity<?> uploadDefaultPhoto(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            imageService.saveDefault(imageFile);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }

    @PostMapping("/uploadBackground")
    public ResponseEntity<?> uploadBackground(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            imageService.saveBackground(imageFile);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }
}
