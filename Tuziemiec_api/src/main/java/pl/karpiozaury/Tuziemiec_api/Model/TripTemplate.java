package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trip_templates")
@Getter
@Setter
@RequiredArgsConstructor
public class TripTemplate {

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
    private Long guideId;

    @NotBlank
    private String coverPhoto;

    @NotBlank
    private Float rating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trip_attractions",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id")
    )
    private Set<Attraction> attractions = new HashSet<>();

    public TripTemplate(@NotBlank String name,
                        @NotBlank String description,
                        @NotBlank String place,
                        @NotBlank Long guideId,
                        @NotBlank String coverPhoto,
                        @NotBlank Float rating) {

        this.name = name;
        this.description = description;
        this.place = place;
        this.guideId = guideId;
        this.coverPhoto = coverPhoto;
        this.rating = rating;
    }
}
