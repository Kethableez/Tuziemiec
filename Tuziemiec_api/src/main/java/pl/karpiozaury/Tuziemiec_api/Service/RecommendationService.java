package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Participation;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;
import pl.karpiozaury.Tuziemiec_api.Repository.ParticipationRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripTemplateRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecommendationService {
    @Autowired
    private final ParticipationRepository participationRepository;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripTemplateRepository templateRepository;

    public List<Trip> gudieTrips(UsernamePasswordAuthenticationToken token) {
        List<Participation> participations = participationRepository.findAllByUserId(userRepository.findByUsername(token.getName()).orElseThrow().getId()).orElseThrow();

        List<Trip> recommendedTrips;

        List<Participation> participationList = participationRepository.findByUserIdAndStartDateGreaterThanEqual(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                LocalDate.now()
        ).orElseThrow();

        List<Trip> participatedTrips = new ArrayList<>();

        for(Participation p : participationList) {
            participatedTrips.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }

        Long guideid = 0L;
        HashMap<Long, Integer> guides = new HashMap<>();

        for (Participation participation : participations) {
            Long id = tripRepository.findById(participation.getTripId()).orElseThrow().getTemplate().getGuideId();
            guides.merge(id, 1, Integer::sum);
        }

        for (Map.Entry<Long, Integer> entry : guides.entrySet() ) {
            if(entry.getValue().equals(guides.values().stream().max(Integer::compare).get()))
                guideid = entry.getKey();
        }

        recommendedTrips = tripRepository.findTripsByGuideId(guideid);
        recommendedTrips.removeIf(participatedTrips::contains);
        return recommendedTrips;
    }

    public List<Trip> ratingTrips(UsernamePasswordAuthenticationToken token) {
        List<TripTemplate> templates = templateRepository.findByRating();

        templates.removeIf(t -> t.getGuideId().equals(userRepository.findByUsername(token.getName()).orElseThrow().getId()));

        List<Trip> trips = new ArrayList<>();

        List<Participation> participationList = participationRepository.findByUserIdAndStartDateGreaterThanEqual(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                LocalDate.now()
        ).orElseThrow();

        List<Trip> participatedTrips = new ArrayList<>();

        for(Participation p : participationList) {
            participatedTrips.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }

        for(TripTemplate template : templates) {
            try {
                trips.addAll(tripRepository.findByTemplateId(template.getId()).orElseThrow());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        trips.removeIf(participatedTrips::contains);


        return trips;
    }

    public List<Trip> placeTrips(UsernamePasswordAuthenticationToken token) {
        List<Trip> trips = new ArrayList<>();

        List<Participation> participationList = participationRepository.findByUserIdAndStartDateGreaterThanEqual(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                LocalDate.now()
        ).orElseThrow();

        List<Trip> participatedTrips = new ArrayList<>();

        for(Participation p : participationList) {
            participatedTrips.add(tripRepository.findById(p.getTripId()).orElseThrow());
        }

        try {
            Long tripId = participationRepository.findLatestTrip(
                    userRepository.findByUsername(token.getName()).orElseThrow().getId()
            ).orElseThrow();

            String place = tripRepository.findById(tripId).orElseThrow().getTemplate().getPlace();
            trips = tripRepository.findByPlace(place).orElseThrow();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        trips.removeIf(participatedTrips::contains);
        trips.removeIf(t -> t.getTemplate().getGuideId().equals(userRepository.findByUsername(token.getName()).orElseThrow().getId()));

        return trips;
    }
}
