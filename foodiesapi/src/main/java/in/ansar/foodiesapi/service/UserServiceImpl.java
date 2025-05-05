package in.ansar.foodiesapi.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ansar.foodiesapi.entity.UserEntity;
import in.ansar.foodiesapi.io.UserRequest;
import in.ansar.foodiesapi.io.UserResponse;
import in.ansar.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationFacade authenticationFacade;

	@Override
	public UserResponse registerUser(UserRequest userRequest) {
		UserEntity newUser = convertToEntity(userRequest);
		UserEntity savedUser = userRepository.save(newUser);
		return convertToResponse(savedUser);

	}

	private UserEntity convertToEntity(UserRequest request) {
		return UserEntity.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName()).build();

	}

	private UserResponse convertToResponse(UserEntity userEntity) {
		return UserResponse.builder().id(userEntity.getId()).email(userEntity.getEmail()).name(userEntity.getName())
				.build();

	}

	@Override
	public String findByUserId() {
		String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
		return userRepository.findByEmail(loggedInUserEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + loggedInUserEmail)).getId();

	}

}
