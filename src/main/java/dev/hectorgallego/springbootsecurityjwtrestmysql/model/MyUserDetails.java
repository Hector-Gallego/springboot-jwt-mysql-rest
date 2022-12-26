package dev.hectorgallego.springbootsecurityjwtrestmysql.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * La clase MyUserDetails es la utilizada por el Servicio MyUserDetailsService para almacenar
 * los datos del usuario una vez se autentica correctamente y asi poder ser administrado y 
 * validado por el administrador de autenticación de la aplicación. 
 */
public class MyUserDetails implements UserDetails {

    private User user;
    public MyUserDetails(User user) {
        this.user = user;
    }


    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //aqui es donde tengo que asignar los roles del usuario
        List<GrantedAuthority> authorities  = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole()))
            .collect(Collectors.toList());

            return authorities;
            

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
