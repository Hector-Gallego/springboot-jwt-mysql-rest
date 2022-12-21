package dev.hectorgallego.springbootsecurityjwtrestmysql.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.springframework.stereotype.Component;


@Component
final class KeyGeneretorsUtil {
    
    /*
     * clase especializada para generar las laves publicas y privadas para la
     * codificación y decodificación del token
     */
    private KeyGeneretorsUtil(){
    }

    static KeyPair genereteRsaKey(){
        KeyPair keyPair;
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch(Exception ex){
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
