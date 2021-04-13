package pl.karpiozaury.Tuziemiec_api.Model;

import javassist.runtime.Inner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "tourName", nullable = false)
    private String tourName;

    @Column(name = "tourCity", nullable = false)
    private String city;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "startPoint", nullable = false)
    private String startPoint;

    @Column(name = "userLimit", nullable = false)
    private Integer userLimit;

    @Column(name = "tourTime", nullable = false)
    private Time tourTime;

    @Column(name = "guide_id", nullable = false)
    private Long guide_id;

    public Tour(String tourName, String city, String description, String startPoint, Integer userLimit, Time tourTime, Long guide_id) {
        this.tourName = tourName;
        this.city = city;
        this.description = description;
        this.startPoint = startPoint;
        this.userLimit = userLimit;
        this.tourTime = tourTime;
        this.guide_id = guide_id;
    }
}
