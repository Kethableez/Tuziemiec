package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserDataRequest {
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
}
