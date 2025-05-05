package in.ansar.foodiesapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ansar.foodiesapi.io.AuthenticationRequest;
import in.ansar.foodiesapi.io.AuthenticationResponse;
import in.ansar.foodiesapi.service.AppUserDetailsService;
import in.ansar.foodiesapi.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final AppUserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest user) {
		
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
			String jwt = jwtUtil.generateToken(userDetails);
			
			// return new AuthenticationResponse(user.getEmail(), jwt,HttpStatus.OK);
			
			return new ResponseEntity<>(new AuthenticationResponse(user.getEmail(), jwt), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception occurred while createAuthenticationToken ", e);

			// return new AuthenticationResponse(user.getEmail(), "Token not generated !
			// Please enter valid user name and passowrd",HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(user.getEmail(),
					"invalid credentials,Please try with valid credentials "), HttpStatus.UNAUTHORIZED);

		}
	}

}
