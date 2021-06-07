package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="trip_photos")
@Setter
@Getter
@RequiredArgsConstructor
public class TripPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tripName;

    @NotBlank
    private String fileName;

    public TripPhoto(@NotBlank String tripName, @NotBlank String fileName) {
        this.tripName = tripName;
        this.fileName = fileName;
    }
}
