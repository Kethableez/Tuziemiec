package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Optional<Attraction> findByName(String name);
    Optional<List<Attraction>> findAllByPlace(String place);
    Boolean existsByName(String name);
    Boolean existsByNameAndPlace(String name, String place);
}
