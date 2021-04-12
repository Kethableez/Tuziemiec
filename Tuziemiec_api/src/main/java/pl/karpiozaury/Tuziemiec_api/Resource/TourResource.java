package pl.karpiozaury.Tuziemiec_api.Resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.karpiozaury.Tuziemiec_api.Model.Tour;
import pl.karpiozaury.Tuziemiec_api.Service.TourService;

import java.util.List;

@RestController
@RequestMapping("/tours")
@AllArgsConstructor
public class TourResource {

    private final TourService tourService;

    @GetMapping("/all")
    public ResponseEntity<List<Tour>> getAllUsers() {
        List<Tour> tours = tourService.findAllTours();
        return new ResponseEntity<>(tours, HttpStatus.OK);
    }

}
