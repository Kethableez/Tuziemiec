package pl.karpiozaury.Tuziemiec_api.Resource;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.UserDataRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Response.MessageResponse;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.ImageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserResource {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/data")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUser(UsernamePasswordAuthenticationToken token){
        User user = userRepository.findByUsername(token.getName()).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> changeData(@RequestBody UserDataRequest request, UsernamePasswordAuthenticationToken token){
        User user = userRepository.findByUsername(token.getName()).orElseThrow();

        if(!request.getFirstName().equals("")) {
            user.setFirstName(request.getFirstName());
        }

        if(!request.getLastName().equals("")) {
            user.setLastName(request.getLastName());
        }

        if(request.getDayOfBirth() != null){
            user.setDayOfBirth(request.getDayOfBirth());
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Dane zostały zmienione pomyślnie"));
    }

}
