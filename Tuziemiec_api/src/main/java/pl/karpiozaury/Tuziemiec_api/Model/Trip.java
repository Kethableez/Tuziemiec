package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name="trips")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String city;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 20)
    private Integer userLimit;

    @NotBlank
    private LocalDate tripDate;

    @NotBlank
    private Long guideId;

    @NotBlank
    @Transient
    private Boolean isAvaliable;

    public Trip(String city, String name, String description, Integer limit, LocalDate tripDate, Long guide_id) {
        this.city = city;
        this.name = name;
        this.description = description;
        this.userLimit = limit;
        this.tripDate = tripDate;
        this.guideId = guide_id;
    }

    public Boolean getIsAvaliable() {
        return !Period.between(this.tripDate, LocalDate.now()).isNegative();
    }

    public void setIsAvaliable(boolean avaliable) {
        this.isAvaliable = avaliable;
    }
}
