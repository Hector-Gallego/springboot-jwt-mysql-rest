package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.UserRepository;

/**
 * La clase UserService contiene los metodos para la gestión de la persistencia
 * de un usario en la base de datos. Hace uso de la interface UserRepository para acceder
 * a los metodos CRUD, y del bean PasswordEncoder para cifrar la contraseña en la base de datos
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<User> getAllUsers(){
        return (List<User>)userRepository.findAll();
    }

    public User getOneUser(Long id){
        return userRepository.findById(id).get();
    }


    public User saveUser(User user){
        // se cifra la contraseña antes de almacenarla
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUSer(User user, Long id){

        User userPersist = getOneUser(id);

        userPersist.setNombre(user.getNombre());
        userPersist.setEmail(user.getEmail());
        userPersist.setPassword(user.getPassword());

        return saveUser(userPersist);
    }

    public void deleteUSer(Long id){
        userRepository.deleteById(id);
    }





    
}
