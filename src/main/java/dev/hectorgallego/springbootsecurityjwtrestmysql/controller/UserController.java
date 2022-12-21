package dev.hectorgallego.springbootsecurityjwtrestmysql.controller;

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

@RestController
@RequestMapping("/api")
public class UserController {

    
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable Long id){
        return userService.getOneUser(id);
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUSer(user, id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUSer(id);
    }


    
}
