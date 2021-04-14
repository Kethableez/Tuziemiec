package pl.karpiozaury.Tuziemiec_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Model.Role;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Repository.RoleRepository;
import pl.karpiozaury.Tuziemiec_api.Repository.UserRepository;
import pl.karpiozaury.Tuziemiec_api.Requests.DataRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void updateUser(DataRequest request, UsernamePasswordAuthenticationToken token) {
        User user = userRepository.findByUserName(token.getName());

        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if(request.getDayOfBirth() != null) {
            user.setDayOfBirth(request.getDayOfBirth());
        }

        userRepository.save(user);
    }

    public User addUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalStateException("Email already taken");
        }

        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalStateException("Username already taken");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
