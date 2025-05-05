package in.ansar.foodiesapi.controller;

import java.util.Map;

import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.ansar.foodiesapi.io.CartRequest;
import in.ansar.foodiesapi.io.CartResponse;
import in.ansar.foodiesapi.service.CartService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping
	public CartResponse addToCart(@RequestBody CartRequest request) {
		String foodId = request.getFoodId();
		if (foodId == null || foodId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food id not found");
		} else {
			return cartService.addToCart(request);

		}

	}

	@GetMapping
	public CartResponse getCart() {
		return cartService.getCart();
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void clearCart() {
		cartService.clearCart();
	}

	@PostMapping("/remove")
	public CartResponse removeFromCart(@RequestBody CartRequest cartRequest) {
		String foodId = cartRequest.getFoodId();
		if (foodId == null || foodId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food id not found");
		}
		return cartService.removeFromCart(cartRequest);

	}

}
