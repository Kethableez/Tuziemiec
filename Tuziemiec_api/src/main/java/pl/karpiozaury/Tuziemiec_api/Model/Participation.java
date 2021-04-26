package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "participations")
@Getter
@Setter
@RequiredArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private Long tripId;

    @NotBlank
    private Long userId;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private Boolean isReviewed;

    public Participation(@NotBlank Long tripId,
                         @NotBlank Long userId,
                         @NotBlank LocalDate startDate) {
        this.tripId = tripId;
        this.userId = userId;
        this.startDate = startDate;
        this.isReviewed = false;
    }
}
