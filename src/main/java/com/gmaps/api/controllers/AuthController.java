package com.gmaps.api.controllers;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmaps.api.dto.AuthResponseDTO;
import com.gmaps.api.dto.LoginDto;
import com.gmaps.api.dto.RegisterDto;
import com.gmaps.api.models.Role;
import com.gmaps.api.models.UserEntity;
import com.gmaps.api.repository.RoleRepository;
import com.gmaps.api.repository.UserRepository;
import com.gmaps.api.security.JWTGenerator;
import com.gmaps.api.service.LoginService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;
	private LoginService loginService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
			LoginService loginService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginService.formatLogin(loginDto.getLogin()), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}

	// alterar codigo para selecionar o tipo de "ROLE"
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {

		String login = loginService.formatLogin(registerDto.getLogin());
		String password = registerDto.getPassword();

		if (userRepository.existsByLogin(login)) {
			return new ResponseEntity<>("Usuário já cadastrado!", HttpStatus.BAD_REQUEST);
		}


		if (loginService.validLogin(login) == false) {
			return new ResponseEntity<>("O usuário deve ser um CPF válido!", HttpStatus.BAD_REQUEST);
		}

		if (password.length() > 12 || password.length() < 6) {
			return new ResponseEntity<>("A senha deve ter entre 6 a 14 caracteres.", HttpStatus.BAD_REQUEST);
		}

		UserEntity user = new UserEntity();
		user.setLogin(login);
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setPhone(registerDto.getPhone());
		user.setCity(registerDto.getCity());
		Role roles = roleRepository.findByName(registerDto.getRole()).get();
		user.setRoles(Collections.singletonList(roles));

		userRepository.save(user);

		return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.OK);
	}
}
