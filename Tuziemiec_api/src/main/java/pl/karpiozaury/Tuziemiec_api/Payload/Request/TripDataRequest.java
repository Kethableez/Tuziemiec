package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TripDataRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer userLimit;
}
