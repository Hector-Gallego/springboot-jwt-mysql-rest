package dev.hectorgallego.springbootsecurityjwtrestmysql.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;

import dev.hectorgallego.springbootsecurityjwtrestmysql.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable Long id) {
        return userService.getOneUser(id);
    }

    /*
     * @PostMapping("/users")
     * public User saveUser(@RequestBody User user) {
     * 
     * User userRegistred = userService.saveUser(user);
     * return userRegistred;
     * }
     */

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUSer(user, id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUSer(id);
    }

    @PostMapping("/users")
    public String RegisterUser(@RequestBody User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {

        String siteURL = request.getRequestURL()
            .toString()
            .replace(request.getServletPath(), "");

        userService.registerUser(user, siteURL);

        return "registro satisfactorio";

    }

    @GetMapping("/users/verify/{token}")
    public String verifyUser(@PathVariable String token) {

        boolean userVerify = userService.userVerification(token);

        if (userVerify) {
            return "user Verify";
        } else {
            return "user verify failled";
        }
    }

}
