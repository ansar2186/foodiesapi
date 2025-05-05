package in.ansar.foodiesapi.service;

import in.ansar.foodiesapi.io.UserRequest;
import in.ansar.foodiesapi.io.UserResponse;

public interface UserService {

	UserResponse registerUser(UserRequest userRequest);

	String findByUserId();

}
