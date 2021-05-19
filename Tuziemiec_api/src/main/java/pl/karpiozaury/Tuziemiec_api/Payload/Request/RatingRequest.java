package pl.karpiozaury.Tuziemiec_api.Payload.Request;

import lombok.Getter;
import lombok.Setter;
import pl.karpiozaury.Tuziemiec_api.Model.Review;

@Setter
@Getter
public class RatingRequest {
    private Review review;
    private Long UserId;
    private int rating;
}
