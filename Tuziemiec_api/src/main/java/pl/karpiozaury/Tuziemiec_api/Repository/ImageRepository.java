package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.karpiozaury.Tuziemiec_api.Model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByFilePurpose(String purpose);
    Image findByFileName(String fileName);
    Image findByFileNameAndFilePurpose(String fileName, String purpose);
    List<Image> findAllByFilePurpose(String purpose);
}
