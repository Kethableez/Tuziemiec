package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Tour;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.TourRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    public List<Tour> findAllTours(){
        return tourRepository.findAll();
    }

    public Tour addTour(Tour tour){
        return tourRepository.save(tour);
    }
}
