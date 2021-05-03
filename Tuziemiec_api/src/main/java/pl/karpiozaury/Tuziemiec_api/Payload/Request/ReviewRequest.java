package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ReviewRequest {
    private Long tripId;
    private String commentHeader;
    private String commentBody;
    private Integer rating;
}
