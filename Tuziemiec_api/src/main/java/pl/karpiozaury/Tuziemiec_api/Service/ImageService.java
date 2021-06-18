package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.karpiozaury.Tuziemiec_api.Model.Image;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.ImageRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final String BACKGROUND = "background";
    private static final String AVATAR = "avatar";
    private static final String ATTRACTION = "attraction";

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final ImageRepository imageRepository;

    public Image getBackground() {
        return imageRepository.findByFilePurpose(BACKGROUND);
    }

    public Image saveBackground(MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), BACKGROUND ,file.getBytes());
        return imageRepository.save(image);
    }

    public Image saveDefault(MultipartFile file) throws IOException {
        String filename = "default_avatar" + ".jpeg";
        Image image = new Image(filename, file.getContentType(), AVATAR ,file.getBytes());
        return imageRepository.save(image);
    }


    public Image getAvatar(Long userId) {
        String filename = userRepository.findById(userId).orElseThrow().getAvatar();
        return imageRepository.findByFileName(filename);
    }

    public Image saveAvatar(MultipartFile file, UsernamePasswordAuthenticationToken token) throws IOException {
        User currentUser = userRepository.findByUsername(token.getName()).orElseThrow();
        String filename = "Avatar_" + currentUser.getUsername() + ".jpeg";

        Image image = new Image(filename, file.getContentType(), token.getName(), file.getBytes());
        currentUser.setAvatar(filename);
        userRepository.save(currentUser);

        return imageRepository.save(image);
    }

    public Image saveTripPhoto(MultipartFile file, String tripName) throws  IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), tripName, file.getBytes());
        return imageRepository.save(image);
    }

    public Image getTripPhoto(String tripName, String fileName) {
        return imageRepository.findByFileNameAndFilePurpose(fileName, tripName);
    }

    public Image saveAttractionPhoto(MultipartFile file) throws  IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), ATTRACTION, file.getBytes());
        return imageRepository.save(image);
    }

    public Image getAttractionPhoto(Long attractionId) {
        String filename = attractionRepository.findById(attractionId).orElseThrow().getCoverPhoto();
        return imageRepository.findByFileName(filename);
    }
}
