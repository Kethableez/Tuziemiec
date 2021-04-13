package pl.karpiozaury.Tuziemiec_api.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.karpiozaury.Tuziemiec_api.Model.User;
import pl.karpiozaury.Tuziemiec_api.Service.DataRequest;
import pl.karpiozaury.Tuziemiec_api.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody DataRequest request, UsernamePasswordAuthenticationToken token) {
        userService.updateUser(request, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //ADMIN ENDPOINTS
    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
