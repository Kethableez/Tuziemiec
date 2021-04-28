package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="trips")
@Setter
@Getter
@RequiredArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "template_id")
    @OneToOne(cascade = CascadeType.ALL)
    private TripTemplate template;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private Integer userLimit;

    @NotBlank
    private Integer booking;

    public Trip(@NotBlank TripTemplate template,
                @NotBlank LocalDate startDate,
                @NotBlank LocalDate endDate,
                @NotBlank Integer userLimit,
                @NotBlank Integer booking) {
        this.template = template;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userLimit = userLimit;
        this.booking = booking;
    }
}
