package pl.karpiozaury.Tuziemiec_api;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

@RestController
@AllArgsConstructor
public class Hello {

    private final UserRepository userRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(UsernamePasswordAuthenticationToken token){
        String username = token.getName();

        User user = userRepository.findByUserName(username);

        return "Hello " + user.getFirstName() + " " + user.getLastName();
    }
}
