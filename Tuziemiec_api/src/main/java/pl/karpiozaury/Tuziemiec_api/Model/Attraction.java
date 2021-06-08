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

    @NotBlank
    private Float rating;

    @NotBlank
    private String coverPhoto;

    @NotBlank
    private Float latitude;

    @NotBlank
    private Float longitude;

    public Attraction(@NotBlank String name,
                      @NotBlank String description,
                      @NotBlank String place,
                      @NotBlank Float rating,
                      @NotBlank String coverPhoto,
                      @NotBlank Float latitude,
                      @NotBlank Float longitude) {
        this.name = name;
        this.description = description;
        this.place = place;
        this.rating = rating;
        this.coverPhoto = coverPhoto;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
