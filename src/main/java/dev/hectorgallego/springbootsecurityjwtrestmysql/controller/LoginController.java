package dev.hectorgallego.springbootsecurityjwtrestmysql.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Login;
import dev.hectorgallego.springbootsecurityjwtrestmysql.service.TokenService;


/**
 * Contiene el controlador para la solicitud de un token por parte de un suario en el sistema
 */

@RestController
@RequestMapping("/api")
public class LoginController {

    private TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(TokenService tokenService, AuthenticationManager authenticationManager) {
        
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping("/login")
    public String getToken(@RequestBody Login login){

        /* se recibe un objeto de tipo Login con el username y el password del usuario que se quiere autenticar,
        * Se genera un objeto de tipo Authentication haciendo uso del metodo authenticationManager, que se encargara
        * de verificar si el usuario y contraseña son correstos mediantes la clase MyUserDetailsService
        * si llama al metodo generateToken de la clase TokenService y se le pasa por parametro la uthenticación
        * si la autenticación es correcta devuelve el token al usuario. 
        */ 
        
        logger.info("user recibido = " + login.username());
        logger.info("password recibido = " + login.password());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));
        return tokenService.generateToken(authentication);

    }

    
}
