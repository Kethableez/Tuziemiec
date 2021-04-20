package pl.karpiozaury.Tuziemiec_api.Repository;

import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findByName(String name);
}
