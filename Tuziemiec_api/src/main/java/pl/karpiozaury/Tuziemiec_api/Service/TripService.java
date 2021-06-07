package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;
import pl.karpiozaury.Tuziemiec_api.Model.Trip;
import pl.karpiozaury.Tuziemiec_api.Model.TripTemplate;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.AttractionRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.TripRequest;
import pl.karpiozaury.Tuziemiec_api.Payload.Request.TripTemplateRequest;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripTemplateRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TripService {

    static final int started_booking_places = 0;
    static final float initial_rating = 0;

    @Autowired
    private final TripTemplateRepository templateRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripRepository tripRepository;

    // Method to add new template
    public TripTemplate addTemplate(TripTemplateRequest request,
                                    UsernamePasswordAuthenticationToken token) {

        TripTemplate template = new TripTemplate(
                request.getName(),
                request.getDescription(),
                request.getPlace(),
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                request.getCoverPhoto(),
                initial_rating
        );


        Set<Attraction> attractions = new HashSet<>();

        request.getAttraction_id().forEach(
                (id) -> attractions.add(attractionRepository.findById(id).orElseThrow())
        );

        template.setAttractions(attractions);

        return templateRepository.save(template);
    }

    // Method to add new attraction
    public Attraction addAttraction(AttractionRequest request) {
        Attraction attraction = new Attraction(
                request.getName(),
                request.getDescription(),
                request.getPlace(),
                0F,
                request.getLatitude(),
                request.getLongitude()
        );

        return attractionRepository.save(attraction);
    }

    // Method to add new trip
    public Trip addTrip(TripRequest request) {
        Trip trip = new Trip(
                templateRepository.findByName(request.getTemplateName()),
                request.getStartDate(),
                request.getEndDate(),
                request.getUserLimit(),
                started_booking_places
        );

       return tripRepository.save(trip);
    }

    // Increase number of taken places
    public Trip increaseBooking(Trip trip) {
        trip.setBooking(trip.getBooking() + 1);
        return tripRepository.save(trip);
    }

    // Decrease number of taken places
    public Trip decreaseBooking(Trip trip) {
        trip.setBooking(trip.getBooking() - 1);
        return tripRepository.save(trip);
    }

    public TripTemplate setRating(TripTemplate template, float rating) {
        if(template.getRating() == 0) {
            template.setRating(rating);
        }

        else {
            template.setRating((template.getRating() + rating) / 2);
        }

        return templateRepository.save(template);
    }

    public List<Trip> getAvailableTrips() {
        return tripRepository.findAvaliableTrips();
    }

    // Check if booking is available
    public boolean isBookingNotAvailable(Trip trip) {
        return trip.getBooking() == trip.getUserLimit();
    }

}
