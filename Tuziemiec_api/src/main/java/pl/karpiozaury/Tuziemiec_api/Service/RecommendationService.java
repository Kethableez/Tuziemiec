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

        Long guideid = 0L;
        HashMap<Long, Integer> guides = new HashMap<Long, Integer>();

        for (Participation participation : participations) {
            Long id = tripRepository.findById(participation.getTripId()).orElseThrow().getTemplate().getGuideId();
            guides.merge(id, 1, Integer::sum);
        }

        for (Map.Entry<Long, Integer> entry : guides.entrySet() ) {
            if(entry.getValue().equals(guides.values().stream().max(Integer::compare).get()))
                guideid = entry.getKey();
        }

        System.out.println(guides);
        return tripRepository.findTripsByGuideId(guideid);
    }

    public List<Trip> ratingTrips(UsernamePasswordAuthenticationToken token) {
        List<TripTemplate> templates = templateRepository.findThreeByRating();
        List<Trip> trips = new ArrayList<>();

        for(TripTemplate template : templates) {
            try {
                trips.add(tripRepository.findByTemplateId(template.getId()).orElseThrow());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return trips;
    }

    public List<Trip> placeTrips(UsernamePasswordAuthenticationToken token) {
        List<Trip> trips = new ArrayList<>();
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

        return trips;
    }
}
