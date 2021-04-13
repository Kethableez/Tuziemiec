package pl.karpiozaury.Tuziemiec_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karpiozaury.Tuziemiec_api.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String userName);
}
