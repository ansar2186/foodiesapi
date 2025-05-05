package in.ansar.foodiesapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.ansar.foodiesapi.entity.CartEntity;
import in.ansar.foodiesapi.io.CartRequest;
import in.ansar.foodiesapi.io.CartResponse;
import in.ansar.foodiesapi.repository.CartRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserService userService;

	@Override
	public CartResponse addToCart(CartRequest request) {
		String loggedInUserId = userService.findByUserId();
		Optional<CartEntity> cartOptional = cartRepository.findByUserId(loggedInUserId);
		CartEntity cart = cartOptional.orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
		Map<String, Integer> cartItems = cart.getItems();
		cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0) + 1);
		cart.setItems(cartItems);
		cartRepository.save(cart);

		return convertToCartResponse(cart);

	}

	private CartResponse convertToCartResponse(CartEntity cartEntity) {
		return CartResponse.builder().id(cartEntity.getId()).userId(cartEntity.getUserId()).map(cartEntity.getItems())
				.build();

	}

	@Override
	public CartResponse getCart() {

		String loggedInUserId = userService.findByUserId();
		CartEntity cartEntity = cartRepository.findByUserId(loggedInUserId)
				.orElse(new CartEntity(null, loggedInUserId, new HashMap<>()));

		return convertToCartResponse(cartEntity);
	}

	@Override
	public void clearCart() {
		String loggedInUserId = userService.findByUserId();
		cartRepository.deleteByUserId(loggedInUserId);

	}

	@Override
	public CartResponse removeFromCart(CartRequest cartRequest) {
		String loggedInUserId = userService.findByUserId();
		CartEntity cartEntity = cartRepository.findByUserId(loggedInUserId)
				.orElseThrow(() -> new RuntimeException("Cart Not Found "));
		Map<String, Integer> cartItems = cartEntity.getItems();
		if (cartItems.containsKey(cartRequest.getFoodId())) {
			int currentQty = cartItems.get(cartRequest.getFoodId());
			if (currentQty > 0) {
				cartItems.put(cartRequest.getFoodId(), currentQty - 1);
			} else {
				cartItems.remove(cartRequest.getFoodId());
			}
			cartEntity = cartRepository.save(cartEntity);
		}

		return convertToCartResponse(cartEntity);
	}

}
