package com.empresa.crudjpa.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.crudjpa.entities.User;
import com.empresa.crudjpa.repositories.IUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository repository;

    @Transactional(readOnly = true)
    @Override
    // SE RECIBE EL USERNAME
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // BUSCA EL USERNAME EN EL REPOSITORIO
        Optional<User> userOptional = repository.findByUsername(username);
        // OBTENEMOS EL USERNAME DEL REPO O BBDD
        // Y VALIDA SI ES VACIO O NO
        if (!userOptional.isPresent()) {
            // SI SI ES VACIO LANZAMOS UNA EXCEPCION
            throw new UsernameNotFoundException(String.format(" username: %s no existe ", username));
        }
        // SI NO ES VACIO OSEA Q SI SI LO ENCUENTRA 
        User user = userOptional.orElseThrow();
        // OBTENEMOS LOS ROLES 
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role->
        new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        // LUEGO PASAMOS EL OBJETO USERDETAILS CON LA INFO DEL USUARIO Q VIENE DE LA BBDD 
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            // EL PASSWORD Q VIENE SIN ENCRIPTAR SE COMPARA CON EL PASSWORD DE LA BASE DE DATOS 
            // SE ENCRIPTA CON UN METODO Y SE COMPARA A LA CONTRASEÑA ENCRIPTADA Q ESTA EN LA BBDD
            user.getPassword(),
            user.isEnabled(),
            true, true,true,authorities);
    }

}
