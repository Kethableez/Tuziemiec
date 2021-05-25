package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;
import pl.karpiozaury.Tuziemiec_api.Model.UsersAttraction;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UsersAttractionRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UsersAttractionService {

    @Autowired
    private final UsersAttractionRepository usersAttractionRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AttractionService attractionService;

    @Autowired
    private final AttractionRepository attractionRepository;

    public UsersAttraction addToList(UsernamePasswordAuthenticationToken token,
                          Long AttractionId,
                          LocalDate tripDate){
        boolean reviewed = false;
        return usersAttractionRepository.save(
                new UsersAttraction(
                        userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                        AttractionId,
                        tripDate,
                        reviewed
                ));
    }

    public UsersAttraction update(UsernamePasswordAuthenticationToken token,
                       Long AttractionId,
                       LocalDate tripDate){
        UsersAttraction userAttraction = usersAttractionRepository.findByUserIdAndAttractionId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                AttractionId
        );

        userAttraction.setPresenceDate(tripDate);
        return usersAttractionRepository.save(userAttraction);
    }

    public UsersAttraction addRating(UsernamePasswordAuthenticationToken token,
                                     Long AttractionId,
                                     Integer rating){

        Attraction attraction = attractionRepository.findById(AttractionId).orElseThrow();
        attractionService.addRating(attraction, rating);
        attractionRepository.save(attraction);

        UsersAttraction userAttraction = usersAttractionRepository.findByUserIdAndAttractionId(
                userRepository.findByUsername(token.getName()).orElseThrow().getId(),
                AttractionId
        );

        userAttraction.setIsReviewed(true);
        return usersAttractionRepository.save(userAttraction);
    }
}
