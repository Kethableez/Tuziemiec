package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "users_attraction")
@Setter
@Getter
@RequiredArgsConstructor
public class UsersAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private Long userId;

    @NotBlank
    private Long attractionId;

    @NotBlank
    private LocalDate presenceDate;

    @NotBlank
    private Boolean isReviewed;

    public UsersAttraction(@NotBlank Long userId,
                           @NotBlank Long attractionId,
                           @NotBlank LocalDate presenceDate,
                           @NotBlank Boolean isReviewed) {
        this.userId = userId;
        this.attractionId = attractionId;
        this.presenceDate = presenceDate;
        this.isReviewed = isReviewed;
    }
}
