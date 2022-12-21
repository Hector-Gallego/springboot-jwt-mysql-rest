package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * La clase TokenService consiste en un servicio que permite la generación del token de uasuario
 * en caso de que el usuario sea validom, utiliza una instancui de la clase JwtEncoder para codificar
 * el token que se envia al usuario.
 * 
 * @return String token
 */
@Service
public class TokenService {
    
    JwtEncoder encoder;
    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication){
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
