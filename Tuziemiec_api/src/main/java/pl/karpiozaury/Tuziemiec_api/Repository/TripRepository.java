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

    List<Trip> findByStartDateGreaterThanEqual(LocalDate now);
    List<Trip> findByStartDateLessThan(LocalDate now);

}
