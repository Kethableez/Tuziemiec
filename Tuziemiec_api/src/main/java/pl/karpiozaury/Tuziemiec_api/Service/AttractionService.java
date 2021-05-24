package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Attraction;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;

@Service
@AllArgsConstructor
public class AttractionService {

    @Autowired
    private final AttractionRepository attractionRepository;

    public Attraction addRating(Attraction attraction, Integer rating){
        if (attraction.getRating() == 0) {
            attraction.setRating((float)rating);
        }
        else {
            attraction.setRating((float)(attraction.getRating() + rating) / 2);
        }
        return attractionRepository.save(attraction);
    }
}
