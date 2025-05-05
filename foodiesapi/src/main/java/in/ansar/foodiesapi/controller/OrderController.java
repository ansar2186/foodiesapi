package in.ansar.foodiesapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ansar.foodiesapi.io.OrderRequest;
import in.ansar.foodiesapi.io.OrderResponse;
import in.ansar.foodiesapi.service.OrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		
		return orderService.createOrderWithPayment(orderRequest);
		
	}

}
