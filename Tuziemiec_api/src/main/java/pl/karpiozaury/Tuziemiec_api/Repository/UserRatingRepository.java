package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.karpiozaury.Tuziemiec_api.Model.UserRating;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
    Boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
}
