package pl.karpiozaury.Tuziemiec_api.Creator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TourCreatorRequest {
    private final String tourName;
    private final String city;
    private final String description;
    private final String startPoint;
    private final int userLimit;
    private final Time tourTime;

}