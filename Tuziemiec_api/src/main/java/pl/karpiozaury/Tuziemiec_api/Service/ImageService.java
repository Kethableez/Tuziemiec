package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.karpiozaury.Tuziemiec_api.Model.TripPhoto;
import pl.karpiozaury.Tuziemiec_api.Model.User;
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
    private static final String AVATAR_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Avatars\\";
    private static final String TRIP_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Trips\\";
    private static final String BACKGROUND_PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\";

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TripPhotoRepository photoRepository;

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
        return Files.readAllBytes(path);
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
