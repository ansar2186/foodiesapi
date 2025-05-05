package in.ansar.foodiesapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.ansar.foodiesapi.entity.OrderEntity;
import in.ansar.foodiesapi.io.OrderRequest;
import in.ansar.foodiesapi.io.OrderResponse;
import in.ansar.foodiesapi.repository.OrderRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ModelMapper modelMapper= new ModelMapper();

	@Override
	public OrderResponse createOrderWithPayment(OrderRequest orderRequest) {

		OrderEntity newOrder = converToEntity(orderRequest);
		newOrder = orderRepository.save(newOrder);

		return null;
	}

	private OrderResponse converToOrderResponse(OrderEntity orderEntity) {

		return modelMapper.map(orderEntity, OrderResponse.class);
	}

	private OrderEntity converToEntity(OrderRequest orderRequest) {
		return modelMapper.map(orderRequest, OrderEntity.class);
	}

}
