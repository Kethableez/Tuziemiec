package pl.karpiozaury.Tuziemiec_api.Requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DataRequest {
    private final String firstName;
    private final String lastName;
    private final LocalDate dayOfBirth;
}
