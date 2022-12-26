package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import java.util.ArrayList;
import java.util.List;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Role;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.RoleRepository;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.UserRepository;


/**
 * La clase UserService contiene los metodos para la gestión de la persistencia
 * de un usario en la base de datos. Hace uso de la interface UserRepository para acceder
 * a los metodos CRUD, y del bean PasswordEncoder para cifrar la contraseña en la base de datos
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return (List<User>)userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getOneUser(Long id){
        return userRepository.findById(id).get();
    }


    @Transactional
    public User saveUser(User user){
        // se cifra la contraseña antes de almacenarla
        user.setPassword(encoder.encode(user.getPassword()));

        List<Role> roles = user.getRoles();
        List<Role> rolePersist = new ArrayList<>();

        for(Role role: roles){
            rolePersist.add(roleRepository.findById(role.getId()).get());
        }
        user.setRoles(rolePersist);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUSer(User user, Long id){

        User userPersist = getOneUser(id);

        userPersist.setNombre(user.getNombre());
        userPersist.setEmail(user.getEmail());
        userPersist.setPassword(user.getPassword());
        userPersist.setRoles(user.getRoles());

        return saveUser(userPersist);
    }

    @Transactional
    public void deleteUSer(Long id){
        userRepository.deleteById(id);
    }





    
}
