package dev.hectorgallego.springbootsecurityjwtrestmysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import dev.hectorgallego.springbootsecurityjwtrestmysql.service.MyUserDetailsService;

/**
 * La clase Security Config contiene los metodos necesarios para gestionar la seguridad 
 * de la api, asi como proveer el passwordEncoder para la codificación de la contraseña, 
 * y proveer el codificador y decodificador para el token de acceso.
 * cuenta con el metodo securityFilterChain el cual es donde se configura la seguridad
 * de los end point de la plicación, asi com el authenticationManagger el cual se encarga
 * de verificar y proveer un usuario authenticado en el caso de que las credenciales sean
 * las correctas
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // se inicializa en un metodo de la clase, por eso no se inyecta
  
    @Autowired
    private RsaKeyProperties rsaKeys;

    
   

    // se inyecta MyUserDetailService mediante Constructor
    private MyUserDetailsService myUserDetailsService;
    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

   


    /*
     * El metodo authManagger se encarga de proveer un usuario autorizado
     * suministrado por myUserDetailService
     */
    @Bean
    public AuthenticationManager authManagger(MyUserDetailsService myUserDetailsService){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);

    }


    /*
     * en securityFilterChain configuramos la seguridad de la aplicación
     * la protección de los endpoint, entre otras muchas configuaraciones
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .csrf(csrf -> csrf
            .disable()
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/login","/api/home","/api/roles","/api/authorities").permitAll()//rutas permitidas
            .requestMatchers("/api/users/**").permitAll()
            .requestMatchers("/api/securityadmin").hasAnyAuthority("SCOPE_admin")
            .requestMatchers("/api/securityuseradmin").hasAnyAuthority("SCOPE_admin","SCOPE_user")
            .anyRequest().authenticated()
        )
        .userDetailsService(myUserDetailsService)//se suministra el userDetailService
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//se elimina la gestión de sesión
        )
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)   
        .build();
    }


    /*
     * Este metodo de algun modo utiliza las llaves privadas y publicas
     * para la codificación del token
     */
   

     // metodo para la desencriptacion del token
    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    // metodo para la encriptacion del token
    @Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

    // algorithmo con el que se cifrara la contraseña de usuario 
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
