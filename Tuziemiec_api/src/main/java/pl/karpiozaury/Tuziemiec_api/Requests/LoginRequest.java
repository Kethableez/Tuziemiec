package pl.karpiozaury.Tuziemiec_api.Requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
