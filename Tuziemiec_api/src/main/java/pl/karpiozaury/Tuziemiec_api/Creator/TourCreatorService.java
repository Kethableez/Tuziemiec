package pl.karpiozaury.Tuziemiec_api.Creator;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Tour;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Service.TourService;
import pl.karpiozaury.Tuziemiec_api.Service.UserService;

@Service
@AllArgsConstructor
public class TourCreatorService {

    private final TourService tourService;
    private final UserService userService;

    public String create(TourCreatorRequest request, UsernamePasswordAuthenticationToken token) {
        User user = userService.findUserByUserName(token.getName());

        tourService.addTour(
                new Tour(
                        request.getTourName(),
                        request.getCity(),
                        request.getDescription(),
                        request.getStartPoint(),
                        request.getUserLimit(),
                        request.getTourTime(),
                        user.getId()
                )
        );

        return "Success";
    }
}
