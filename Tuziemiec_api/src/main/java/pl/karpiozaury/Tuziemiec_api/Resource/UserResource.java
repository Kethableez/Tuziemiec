package pl.karpiozaury.Tuziemiec_api.Resource;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/data")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUser(UsernamePasswordAuthenticationToken token){
        User user = userRepository.findByUsername(token.getName()).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
