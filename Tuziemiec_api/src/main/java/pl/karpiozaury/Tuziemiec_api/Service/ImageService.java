package pl.karpiozaury.Tuziemiec_api.Service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Directory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final String PATH = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Avatars\\";
    private static final String PATH2 = "C:\\Users\\Amadeusz\\Desktop\\Tuziemiec\\Tuziemiec_images\\Trips\\";

    @Autowired
    private final UserRepository userRepository;

    public byte[] getImage(String username) throws IOException {
        String filename = userRepository.findByUsername(username).orElseThrow().getAvatar();
        Path path = Paths.get(PATH + filename);
        return Files.readAllBytes(path);
    }

    public byte[] getDefault() throws IOException {
        String filename = "default_avatar" + ".jpeg";
        Path path = Paths.get(PATH + filename);
        return Files.readAllBytes(path);
    }


    public void saveImage(MultipartFile imageFile, UsernamePasswordAuthenticationToken token) throws Exception {
        User currentUser = userRepository.findByUsername(token.getName()).orElseThrow();
        String filename = "Avatar_" + currentUser.getUsername() + ".jpeg";

        byte[] bytes = imageFile.getBytes();
        Path path =  Paths.get(PATH + filename);
        Files.write(path, bytes);

        currentUser.setAvatar(filename);
        userRepository.save(currentUser);
    }

    public void saveDefault(MultipartFile imageFile) throws Exception {
        String filename = "default_avatar" + ".jpeg";

        byte[] bytes = imageFile.getBytes();
        Path path =  Paths.get(PATH + filename);
        Files.write(path, bytes);
    }
}
