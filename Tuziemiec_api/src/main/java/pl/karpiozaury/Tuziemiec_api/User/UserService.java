package pl.karpiozaury.Tuziemiec_api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karpiozaury.Tuziemiec_api.Exception.UserNotFoundException;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // addUser method
    public User addUser(User user){
        return userRepo.save(user);
    }

    // findAllUsers method
    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    // updateUser method
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    // findUserById method
    public User findUserById(Long id){
        return userRepo.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + "was not found"));
    }

    // deleteUserById method
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }
}
