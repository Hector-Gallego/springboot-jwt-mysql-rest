package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.MyUserDetails;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.UserRepository;


/**
 * MyUserDetailService consiste en una clase encargada de verificar si un usario existe en la aplicación
 * en base a su username o cualquier atributo deseado, es un servicio quye se despacha en el contenedor 
 * de spring-boot, cuenta con un metodo sobre escrito loadUserByUsername que es el encargado de la acción
 * anteriormente mencionada, el cual retorna un objetp de la clase MyUserDetails, el cual contiene todos 
 * los detalles del usuario en caso de que se encuentre, como su username, password, etc.
 * la clase MyUserDetails es una clase propia, que implementa la interface UserDetails, y sobre escribe
 * todos los metodos de la interface
 * 
 * @return new MyUserDetails
 */

@Service
public class MyUserDetailsService implements UserDetailsService{

    /*se innyecta mediante constructor la clase UserRepository con los metodos
     * necesarios para buscar un usario en la base de datos
    */
    UserRepository userRepository;
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * si el usario es encontrado en la base de datos retornamos una instancia de MyUserDetails
     * con los datos del usario cargados, para que sean validados y manejados por los administradores
     * de autenticación
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {     
        return userRepository
            .findByUsername(username)
            .map(MyUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario con no encontrado: "+username));
    }
    
}
