package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "review_ratings")
@Setter
@Getter
@RequiredArgsConstructor
public class ReviewRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @NotBlank
//    private Long reviewId;

    @JoinColumn(name = "review_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Review review;

    @NotBlank
    private Long userId;

    @NotBlank
    private int rating;

    public ReviewRating(Review review,
                        @NotBlank Long userId,
                        @NotBlank int rating) {
        this.review = review;
        this.userId = userId;
        this.rating = rating;
    }
}
