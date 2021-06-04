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

    private static final String PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Trips\\";

    @Autowired
    private final ImageService imageService;

    @GetMapping(value = "/getAvatar/{username}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> showAvatar(@PathVariable("username") String username) {
        try {
            byte[] image = imageService.getImage(username);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    @GetMapping(value = "/getDefault",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getDefault() {
        try {
            byte[] image = imageService.getDefault();
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
            imageService.saveImage(imageFile, token);
            return ResponseEntity.ok(new MessageResponse("Dodano zdjęcie!"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!"));
        }
    }


    @PutMapping("/uploadDefault")
    public String uploadDefault(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            imageService.saveDefault(imageFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "Image saved!";
    }
}
