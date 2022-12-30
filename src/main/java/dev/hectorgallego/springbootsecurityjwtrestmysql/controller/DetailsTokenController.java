package dev.hectorgallego.springbootsecurityjwtrestmysql.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DetailsTokenController {

    
    @GetMapping("/authorities")
    public Map<String, Object> auhorities(JwtAuthenticationToken principal){
        
         
        Collection<String> authorities = principal.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
        

        Map<String,Object> info = new HashMap<>();
        info.put("name", principal.getName());
        info.put("authorities", authorities);
        info.put("tokenAttributes", principal.getTokenAttributes());

        return info;

    }
    

    


    
}
