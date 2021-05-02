package pl.karpiozaury.Tuziemiec_api.Repository;

import lombok.ToString;
import org.apache.tomcat.jni.Local;
import org.hibernate.sql.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByStartDateGreaterThanEqual(LocalDate now);
    List<Trip> findByStartDateLessThan(LocalDate now);
    Optional<List<Trip>> findAllByTemplate_Name(String name);
}
