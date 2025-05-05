package in.ansar.foodiesapi.service;

import in.ansar.foodiesapi.io.CartRequest;
import in.ansar.foodiesapi.io.CartResponse;

public interface CartService {

	CartResponse addToCart(CartRequest cartRequest);

	CartResponse getCart();

	void clearCart();

	CartResponse removeFromCart(CartRequest cartRequest);

}
