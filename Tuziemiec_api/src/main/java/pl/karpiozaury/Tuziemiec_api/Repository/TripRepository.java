package pl.karpiozaury.Tuziemiec_api.Repository;

import lombok.ToString;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Trip findByName(String name);
    List<Trip> findByTripDateGreaterThanEqual(LocalDate now);
    List<Trip> findByTripDateLessThan(LocalDate now);

    List<Trip> findByTripDateLessThanAndGuideId(LocalDate now, Long id);
}
