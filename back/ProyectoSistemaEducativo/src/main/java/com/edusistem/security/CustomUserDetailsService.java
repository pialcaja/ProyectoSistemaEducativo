package com.edusistem.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edusistem.model.Administrador;
import com.edusistem.repository.AdministradorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AdministradorRepository adminRepo;
	
	public CustomUserDetailsService(AdministradorRepository adminRepo) {
		this.adminRepo = adminRepo;
	}
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrador admin = adminRepo.findByEmailAdministrador(email)
            .orElseThrow(() -> new UsernameNotFoundException("Admin no encontrado: " + email));
        
        return new org.springframework.security.core.userdetails.User(
            admin.getEmailAdministrador(),
            admin.getPwdAdministrador(),
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}
