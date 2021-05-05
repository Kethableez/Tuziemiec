package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionRequest {
    private String name;
    private String description;
    private String place;
    private Float latitude;
    private Float longitude;
}

