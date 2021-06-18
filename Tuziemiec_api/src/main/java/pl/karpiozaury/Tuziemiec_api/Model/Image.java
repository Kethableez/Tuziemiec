package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="images")
@Setter
@Getter
@RequiredArgsConstructor
public class Image {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String fileName;

    private String fileType;

    private String filePurpose;

    @Lob
    private byte[] data;

    public Image(String fileName, String fileType, String filePurpose, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePurpose = filePurpose;
        this.data = data;
    }
}
