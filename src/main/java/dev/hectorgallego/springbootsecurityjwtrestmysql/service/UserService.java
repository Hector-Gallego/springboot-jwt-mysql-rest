package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Role;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.RoleRepository;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * La clase UserService contiene los metodos para la gestión de la persistencia
 * de un usario en la base de datos. Hace uso de la interface UserRepository
 * para acceder
 * a los metodos CRUD, y del bean PasswordEncoder para cifrar la contraseña en
 * la base de datos
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAdress;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder encoder,
            RoleRepository roleRepository,
            JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getOneUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User saveUser(User user) {
        // se cifra la contraseña antes de almacenarla
        user.setPassword(encoder.encode(user.getPassword()));

        List<Role> roles = user.getRoles();
        List<Role> rolePersist = new ArrayList<>();

        for (Role role : roles) {
            rolePersist.add(roleRepository.findById(role.getId()).get());
        }
        user.setRoles(rolePersist);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUSer(User user, Long id) {
        User userPersist = getOneUser(id);

        userPersist.setNombre(user.getNombre());
        userPersist.setEmail(user.getEmail());
        userPersist.setPassword(user.getPassword());
        userPersist.setRoles(user.getRoles());

        return saveUser(userPersist);
    }

    @Transactional
    public void deleteUSer(Long id) {
        userRepository.deleteById(id);
    }


    // metodos para el envio del email
    public void registerUser(User user, String siteUrl) throws UnsupportedEncodingException, MessagingException{

        String ramdonCode = UUID.randomUUID().toString();
        user.setVerificationCode(ramdonCode);
        user.setEnabled(false);
        saveUser(user);
        sendVerificationEmail(user, siteUrl);

    }

    private void sendVerificationEmail(User user, String siteUrl) throws UnsupportedEncodingException, MessagingException{


        String toAdress = user.getEmail();
        String sendName = "Hector dev";
        String subject = "Porfavor verifique su correo electronico";

        String content = "Dear [[name]],<br>"
        + "Please click the link below to verify your registration:<br>"
        + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br>"
        + "Hector dev.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAdress, sendName);
        helper.setTo(toAdress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getNombre());
        String verifyURL = siteUrl + "/api/users/verify/"+user.getVerificationCode();

        content = content.replace("[[URL]]",verifyURL);
        helper.setText(content, true);

        javaMailSender.send(message);
        
    }

    public boolean userVerification(String token){
        User user =  userRepository.findByVerificationCode(token).get();

        if(user == null || user.isEnabled()){
            return false;
        }else{
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    

}
