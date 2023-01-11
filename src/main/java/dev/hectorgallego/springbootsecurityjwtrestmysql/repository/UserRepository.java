package dev.hectorgallego.springbootsecurityjwtrestmysql.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
    // buscamos al usuario por su codigo de verificación
    Optional<User> findByVerificationCode(String verificationCode);
}
