package pl.karpiozaury.Tuziemiec_api.Creator;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tourcreator")
@AllArgsConstructor
public class TourCreatorController {

    private final TourCreatorService tourCreatorService;

    @PostMapping
    public String register(@RequestBody TourCreatorRequest request, UsernamePasswordAuthenticationToken token) {
        return tourCreatorService.create(request, token);
    }
}