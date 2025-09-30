package com.edusistem.service;

import com.edusistem.dto.AuthResponse;
import com.edusistem.dto.LoginRequest;
import com.edusistem.dto.RefreshTokenRequest;

public interface AuthService {

	AuthResponse login(LoginRequest loginRequest);
	
	AuthResponse refreshToken(RefreshTokenRequest request);
}
