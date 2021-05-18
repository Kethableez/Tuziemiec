package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.karpiozaury.Tuziemiec_api.Model.Review;
import pl.karpiozaury.Tuziemiec_api.Model.ReviewRating;

import java.util.List;

public interface ReviewRatingRepository extends JpaRepository<ReviewRating, Long> {
    Boolean existsByUserIdAndReview(Long UserId, Review review);
//    List<ReviewRating> findAllByRatingEquals(int number);
}
