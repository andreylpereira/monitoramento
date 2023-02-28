package com.gmaps.api.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gmaps.api.models.UserEntity;
import com.gmaps.api.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {
	
	@Autowired
	private UserRepository userRepository;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
		
		Optional<UserEntity> user = userRepository.findByLogin(username);
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.get().getId());
		claims.put("name", user.get().getName());
		claims.put("permissions", authentication.getAuthorities());
		
		//setando o que vai no token
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
				.compact();
		return token;
	}
	
	public String getUsernameFromJWT(String token) {

		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstants.JWT_SECRET)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parse(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("Token expirado ou incorreto.");
		}
	}
}
