package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TripTemplateRequest {
    private String name;
    private String description;
    private String place;
    private List<String> attraction_names;
}
