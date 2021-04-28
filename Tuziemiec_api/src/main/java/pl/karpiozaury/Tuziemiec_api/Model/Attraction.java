package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "attractions")
@Setter
@Getter
@RequiredArgsConstructor
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String place;

    public Attraction(@NotBlank String name,
                      @NotBlank String description,
                      @NotBlank String place) {
        this.name = name;
        this.description = description;
        this.place = place;
    }
}
