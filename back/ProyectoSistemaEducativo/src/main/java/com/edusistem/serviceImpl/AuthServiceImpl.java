package com.edusistem.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.edusistem.dto.AuthResponse;
import com.edusistem.dto.LoginRequest;
import com.edusistem.dto.RefreshTokenRequest;
import com.edusistem.security.JwtUtils;
import com.edusistem.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateAccessToken(loginRequest.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest request) {
		if (jwtUtils.validateJwtToken(request.getRefreshToken())) {
            String username = jwtUtils.getUsernameFromJwt(request.getRefreshToken());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            String newAccessToken = jwtUtils.generateAccessToken(userDetails.getUsername());
            String newRefreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

            return AuthResponse.builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build();
        }
        throw new RuntimeException("Refresh token inválido o expirado");
	}
}
