package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Setter
@Getter
@RequiredArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "participation_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Participation participation;

    @NotBlank
    private String commentHeader;

    @NotBlank
    private String commentBody;

    @NotBlank
    private Integer rating;

    @NotBlank
    private LocalDate commentDate;

    @NotBlank
    private Integer upVote;

    @NotBlank
    private Integer downVote;

    @NotBlank
    private Long templateId;

    public Review(Participation participation,
                  @NotBlank String commentHeader,
                  @NotBlank String commentBody,
                  @NotBlank Integer rating,
                  @NotBlank LocalDate commentDate,
                  @NotBlank Integer like,
                  @NotBlank Integer dislike,
                  @NotBlank Long templateId) {
        this.participation = participation;
        this.commentHeader = commentHeader;
        this.commentBody = commentBody;
        this.rating = rating;
        this.commentDate = commentDate;
        this.upVote = like;
        this.downVote = dislike;
        this.templateId = templateId;
    }

    //    public Review(Participation participation,
//                  @NotBlank String commentHeader,
//                  @NotBlank String commentBody,
//                  @NotBlank Integer rating,
//                  @NotBlank LocalDate commentDate,
//                  @NotBlank Long templateId ) {
//        this.participation = participation;
//        this.commentHeader = commentHeader;
//        this.commentBody = commentBody;
//        this.rating = rating;
//        this.commentDate = commentDate;
//        this.templateId = templateId;
//    }


//    public Review(@NotBlank Long userId,
//                  @NotBlank Long tripId,
//                  @NotBlank String commentHeader,
//                  @NotBlank String commentBody,
//                  @NotBlank Integer rating) {
//        this.userId = userId;
//        this.tripId = tripId;
//        this.commentHeader = commentHeader;
//        this.commentBody = commentBody;
//        this.rating = rating;
//    }
}
