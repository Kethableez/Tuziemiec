package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.UsersAttraction;

@Repository
public interface UsersAttractionRepository extends JpaRepository<UsersAttraction, Long> {
    Boolean existsByUserIdAndAttractionId(Long userId, Long AttractionId);

    UsersAttraction findByUserIdAndAttractionId(Long userId, Long AttractionId);
}
