package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.karpiozaury.Tuziemiec_api.Model.TripPhoto;

import java.util.List;

public interface TripPhotoRepository extends JpaRepository<TripPhoto, Long> {
    List<TripPhoto> findAllByTripName(String tripName);
}
