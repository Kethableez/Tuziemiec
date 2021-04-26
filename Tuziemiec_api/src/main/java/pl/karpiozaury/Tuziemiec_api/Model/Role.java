package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.*;

import javax.persistence.*;

//INSERT INTO tuziemiec.roles(name) VALUES('ROLE_ADMIN');
//INSERT INTO tuziemiec.roles(name) VALUES('ROLE_USER');

@Entity
@Table(name="roles")
@Getter
@Setter
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
