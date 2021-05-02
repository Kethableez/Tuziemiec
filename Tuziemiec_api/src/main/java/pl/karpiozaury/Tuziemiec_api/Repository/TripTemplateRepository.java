package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripTemplateRepository extends JpaRepository<TripTemplate, Long> {
    TripTemplate findByName(String name);
    //Optional<TripTemplate> findByIdAndGuideId(Long id);
    Optional<List<TripTemplate>> findAllByGuideId(Long id);
    Boolean existsByName(String name);
    Boolean existsByNameAndGuideId(String name, Long id);
}

