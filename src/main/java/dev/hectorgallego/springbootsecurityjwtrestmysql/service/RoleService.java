package dev.hectorgallego.springbootsecurityjwtrestmysql.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Role;
import dev.hectorgallego.springbootsecurityjwtrestmysql.repository.RoleRepository;

@Service
public class RoleService {


    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Transactional
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public Role getOneRole(Long id){
        return roleRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles(){
        return (List<Role>) roleRepository.findAll(); 
    }

    @Transactional
    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }
}
