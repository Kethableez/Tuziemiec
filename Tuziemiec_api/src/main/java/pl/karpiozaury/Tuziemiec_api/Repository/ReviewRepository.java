package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Model.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByTemplateId(Long templateId);
    //boolean existsByParticipation(Participation participation);
    //Review findByParticipationId(Long id);
}
