package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query(
            value = "select trip_id from tuziemiec.participations where user_id = ?1 order by start_date DESC Limit 1",
            nativeQuery = true
    )
    Optional<Long> findLatestTrip(Long userId);

    Optional<List<Participation>> findByUserIdAndStartDateLessThan (Long id, LocalDate date);
    Optional<List<Participation>> findByUserIdAndStartDateGreaterThanEqual (Long id, LocalDate date);

    Optional<List<Participation>> findByTripIdAndStartDateLessThan (Long id, LocalDate date);
    Optional<List<Participation>> findByTripIdAndStartDateGreaterThanEqual (Long id, LocalDate date);

    Optional<List<Participation>> findAllByIsReviewedFalseAndUserId(Long userId);
    Optional<List<Participation>> findAllByUserId(Long userId);

    Optional<List<Participation>> findAllByIsReviewedTrue();
    Optional<Participation> findByUserIdAndTripId (Long userId, Long tripId);
    Boolean existsByUserIdAndTripId(Long userId, Long tripId);
}
