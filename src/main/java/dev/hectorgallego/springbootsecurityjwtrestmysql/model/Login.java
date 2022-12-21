package dev.hectorgallego.springbootsecurityjwtrestmysql.model;

/**
 * La clase Login corresponde al tipo de objeto que se recibe en la petición
 * del controllador para iniciar sesión en el sistema y recibir un toke de acceso
 * por parte de  un usario.
 */
public record Login(String username, String password) {  
}
