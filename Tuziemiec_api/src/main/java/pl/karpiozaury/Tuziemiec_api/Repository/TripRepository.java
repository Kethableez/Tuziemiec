package pl.karpiozaury.Tuziemiec_api.Repository;

import lombok.ToString;
import org.apache.tomcat.jni.Local;
import org.hibernate.sql.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {

    @Query(
            value ="select * from tuziemiec.trips where booking < user_limit and start_date > CURDATE()",
            nativeQuery = true)
    List<Trip> findAvaliableTrips();


    List<Trip> findByStartDateGreaterThanEqual(LocalDate now);
    List<Trip> findByStartDateLessThan(LocalDate now);
    Optional<List<Trip>> findAllByTemplate_Name(String name);
}
