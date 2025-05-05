package in.ansar.foodiesapi.service;

import in.ansar.foodiesapi.io.OrderRequest;
import in.ansar.foodiesapi.io.OrderResponse;

public interface OrderService {
	
	OrderResponse createOrderWithPayment(OrderRequest orderRequest);

}
