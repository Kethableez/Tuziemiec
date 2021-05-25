package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.UsersAttraction;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersAttractionRepository extends JpaRepository<UsersAttraction, Long> {
    Boolean existsByUserIdAndAttractionId(Long userId, Long AttractionId);

    UsersAttraction findByUserIdAndAttractionId(Long userId, Long AttractionId);

    Optional<List<UsersAttraction>> findByUserId(Long userId);
}
