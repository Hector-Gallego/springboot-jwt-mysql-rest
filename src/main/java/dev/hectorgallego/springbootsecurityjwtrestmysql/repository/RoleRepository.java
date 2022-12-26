package dev.hectorgallego.springbootsecurityjwtrestmysql.repository;

import org.springframework.data.repository.CrudRepository;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    
}
