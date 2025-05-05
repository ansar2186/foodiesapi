package in.ansar.foodiesapi.io;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
	
	private String email;
	private String token;
	//private HttpStatus status;
	

}
