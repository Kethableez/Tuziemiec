package pl.karpiozaury.Tuziemiec_api.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//INSERT INTO tuziemiec.roles(name) VALUES('ROLE_ADMIN');
//INSERT INTO tuziemiec.roles(name) VALUES('ROLE_USER');

@Entity
@Table(name="roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
