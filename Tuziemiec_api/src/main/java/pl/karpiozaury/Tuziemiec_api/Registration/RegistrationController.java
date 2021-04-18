package pl.karpiozaury.Tuziemiec_api.Registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Requests.RegistrationRequest;

@RestController
@RequestMapping(path = "/registration")
@CrossOrigin
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
