package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TripRequest {

    private String city;
    private String name;
    private String description;
    private Integer limit;
    private LocalDate tripDate;
}
