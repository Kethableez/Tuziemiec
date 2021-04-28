package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "reviews")
@Setter
@Getter
@RequiredArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @JoinColumn(name = "participation_id")
//    @OneToOne(cascade = CascadeType.ALL)
//    private Participation participation;
    @NotBlank
    private Long userId;

    @NotBlank
    private Long tripId;

    @NotBlank
    private String commentHeader;

    @NotBlank
    private String commentBody;

    @NotBlank
    private Integer rating;

//    public Review(Participation participation,
//                  @NotBlank String commentHeader,
//                  @NotBlank String commentBody,
//                  @NotBlank Integer rating) {
//        //this.participation = participation;
//        this.commentHeader = commentHeader;
//        this.commentBody = commentBody;
//        this.rating = rating;
//    }


    public Review(@NotBlank Long userId,
                  @NotBlank Long tripId,
                  @NotBlank String commentHeader,
                  @NotBlank String commentBody,
                  @NotBlank Integer rating) {
        this.userId = userId;
        this.tripId = tripId;
        this.commentHeader = commentHeader;
        this.commentBody = commentBody;
        this.rating = rating;
    }
}
