package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_ratings")
@Setter
@Getter
@RequiredArgsConstructor
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private Long userId;

    @NotBlank
    private Long reviewId;

    @NotBlank
    private Integer rating;

    public UserRating(@NotBlank Long userId,
                      @NotBlank Long reviewId,
                      @NotBlank Integer rating) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.rating = rating;
    }
}
