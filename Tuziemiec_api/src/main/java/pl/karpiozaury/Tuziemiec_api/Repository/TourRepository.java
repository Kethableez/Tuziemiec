package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Tour findByTourName(String tourname);

}
