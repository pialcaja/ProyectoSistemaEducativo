package com.edusistem.serviceImpl;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edusistem.dto.AuthResponse;
import com.edusistem.dto.LoginRequest;
import com.edusistem.dto.RefreshTokenRequest;
import com.edusistem.model.Usuario;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.security.JwtUtils;
import com.edusistem.service.AuthService;
import com.edusistem.utils.TextoUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;
	private final UsuarioRepository usuarioRepo;

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        TextoUtils.formatoTodoMinuscula(loginRequest.getEmail()),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Usuario usuario = usuarioRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        
        String accessToken = jwtUtils.generateAccessToken(TextoUtils.formatoTodoMinuscula(loginRequest.getEmail()), roles);
        String refreshToken = jwtUtils.generateRefreshToken(TextoUtils.formatoTodoMinuscula(loginRequest.getEmail()));

        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
                .nombre(usuario.getNombre()).rol(usuario.getRol().getNombre()).build();
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest request) {
		if (jwtUtils.validateJwtToken(request.getRefreshToken())) {
            String username = jwtUtils.getUsernameFromJwt(request.getRefreshToken());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            String email = jwtUtils.getUsernameFromJwt(request.getRefreshToken()
            		);
            Usuario usuario = usuarioRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            
            String newAccessToken = jwtUtils.generateAccessToken(userDetails.getUsername(), userDetails.getAuthorities());
            String newRefreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

            return AuthResponse.builder().accessToken(newAccessToken).refreshToken(newRefreshToken)
                    .nombre(usuario.getNombre()).rol(usuario.getRol().getNombre()).build();
        }
        throw new RuntimeException("Refresh token inválido o expirado");
	}
}
