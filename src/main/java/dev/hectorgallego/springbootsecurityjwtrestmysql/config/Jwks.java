package dev.hectorgallego.springbootsecurityjwtrestmysql.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.RSAKey;

/*
     * clase especializada para generar las laves publicas y privadas para la
     * codificación y decodificación del token
     */

public class Jwks {

    private Jwks(){}

    public static RSAKey generateRSA(){
        
        KeyPair keyPair = KeyGeneretorsUtil.genereteRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }
    
}
