package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final ParticipationRepository participationRepository;

    // Method to add user to participation table
    public Participation addPatricipant(Long tripId, UsernamePasswordAuthenticationToken token) {
        Long userId = userRepository.findByUsername(token.getName()).orElseThrow().getId();

        //TODO: Checking if user participate in specific trip

        Participation participation = new Participation(
                tripId,
                userId,
                tripRepository.findById(tripId).orElseThrow().getStartDate()
        );

        return participationRepository.save(participation);
    }

    // Method to check if user already participate in specific trip
    public boolean isAlreadyParticipate(Long tripId, UsernamePasswordAuthenticationToken token) {
        return participationRepository.existsByUserIdAndTripId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                tripId
        );
    }

    public List<Participation> getUserPast(UsernamePasswordAuthenticationToken token) {
        return participationRepository.findByUserIdAndStartDateLessThan(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                LocalDate.now()
        ).orElseThrow();
    }

    public List<Participation> getUserCurrent(UsernamePasswordAuthenticationToken token) {
        return participationRepository.findByUserIdAndStartDateGreaterThanEqual(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                LocalDate.now()
        ).orElseThrow();
    }

    public List<Participation> getTripPast(Long tripId) {
        return participationRepository.findByTripIdAndStartDateLessThan(
                tripId,
                LocalDate.now()
        ).orElseThrow();
    }

    public List<Participation> getTripCurrent(Long tripId) {
        return participationRepository.findByTripIdAndStartDateGreaterThanEqual(
                tripId,
                LocalDate.now()
        ).orElseThrow();
    }
}
