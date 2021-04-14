package pl.karpiozaury.Tuziemiec_api.Home;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Service.UserService;

@RestController
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(UsernamePasswordAuthenticationToken token){
        String username = token.getName();

        User user = userService.findUserByUserName(username);

        return "Hello " + user.getFirstName() + " " + user.getLastName();
        }

}
