package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.karpiozaury.Tuziemiec_api.Model.TripPhoto;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.AttractionRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.TripPhotoRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
//    private static final String AVATAR_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Avatars\\";
//    private static final String TRIP_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Trips\\";
//    private static final String ATTRACTION_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Attractions\\";
//    private static final String BACKGROUND_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\";

    private static final String AVATAR_PATH = "Photos\\Avatars\\";
    private static final String TRIP_PATH = "Photos\\Trips\\";
    private static final String ATTRACTION_PATH = "Photos\\Attractions\\";
    private static final String BACKGROUND_PATH = "Photos\\";

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripPhotoRepository photoRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    public byte[] getImage(Long userId) throws IOException {
        String filename = userRepository.findById(userId).orElseThrow().getAvatar();
        Path path = Paths.get(AVATAR_PATH + filename);
        return Files.readAllBytes(path);
    }

    public byte[] getTripPhoto(String tripName, String fileName) throws IOException {
        Path path = Paths.get(TRIP_PATH + tripName + "\\" + fileName);
        return Files.readAllBytes(path);
    }

    public byte[] getBackground() throws IOException {
        String filename = "background.jpg";
        Path path = Paths.get(BACKGROUND_PATH + filename);
//        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public byte[] getAttractionPhoto(Long attractionId) throws IOException {
        String filename = attractionRepository.findById(attractionId).orElseThrow().getCoverPhoto();
        Path path = Paths.get(ATTRACTION_PATH + filename);
        return Files.readAllBytes(path);
    }

    public void saveAttractionPhoto(MultipartFile imageFile) throws Exception {
        byte[] bytes = imageFile.getBytes();
        Path path =  Paths.get(ATTRACTION_PATH + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    public void saveAvatar(MultipartFile imageFile, UsernamePasswordAuthenticationToken token) throws Exception {
        User currentUser = userRepository.findByUsername(token.getName()).orElseThrow();
        String filename = "Avatar_" + currentUser.getUsername() + ".jpeg";

        byte[] bytes = imageFile.getBytes();
        Path path =  Paths.get(AVATAR_PATH + filename);
        Files.write(path, bytes);

        currentUser.setAvatar(filename);
        userRepository.save(currentUser);
    }

    public void saveDefault(MultipartFile imageFile) throws Exception {
        String filename = "default_avatar" + ".jpeg";

        byte[] bytes = imageFile.getBytes();
        Path path =  Paths.get(AVATAR_PATH + filename);
        Files.write(path, bytes);
    }

    public void saveBackground(MultipartFile imageFile) throws Exception {
        String filename = "background" + ".jpg";

        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(BACKGROUND_PATH + filename);
        Files.write(path, bytes);
    }



    public void saveTripPhoto(MultipartFile imageFile, String templateName) throws Exception {
        byte[] bytes = imageFile.getBytes();

        File f = new File(TRIP_PATH + templateName);
        if (!f.exists()){
            f.mkdir();
        }

        Path path =  Paths.get(TRIP_PATH + templateName + "\\" + imageFile.getOriginalFilename());
        Files.write(path, bytes);

        TripPhoto tripPhoto = new TripPhoto(templateName, imageFile.getOriginalFilename());
        photoRepository.save(tripPhoto);
    }
}
