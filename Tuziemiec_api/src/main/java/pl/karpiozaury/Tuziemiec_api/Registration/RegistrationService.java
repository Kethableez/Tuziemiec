package pl.karpiozaury.Tuziemiec_api.Registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Service.UserService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email is not valid!");
        }

        userService.addUser(
                new User(
                        request.getUserName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDayOfBirth()
                )
        );

        return "Success";
    }
}
