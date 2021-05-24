package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {

    @Query(
            value ="select * from tuziemiec.trips where booking < user_limit and start_date >= CURDATE() order by start_date ASC",
            nativeQuery = true
    )
    List<Trip> findAvaliableTrips();


    @Query(
            value = "select * from tuziemiec.trips where start_date >= CURDATE() and booking < user_limit and template_id in (select id from tuziemiec.trip_templates where guide_id = ?1) order by start_date ASC LIMIT 3",
            nativeQuery = true
    )
    List<Trip> findTripsByGuideId(Long guideId);

    @Query(
            value = "select * from tuziemiec.trips where start_date >= CURDATE() and booking < user_limit and template_id = ?1 Order by start_date asc Limit 1",
            nativeQuery = true
    )
    Optional<Trip> findByTemplateId(Long templateId);

    @Query(
            value = "select * from tuziemiec.trips where start_date >= CURDATE() and booking < user_limit and template_id in (select id from tuziemiec.trip_templates where place = ?1) order by start_date ASC LIMIT 3",
            nativeQuery = true
    )
    Optional<List<Trip>> findByPlace(String place);

    List<Trip> findByStartDateGreaterThanEqual(LocalDate now);
    List<Trip> findByStartDateLessThan(LocalDate now);
    Optional<List<Trip>> findAllByTemplate_Name(String name);
}
