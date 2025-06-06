package in.ansar.foodiesapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.ansar.foodiesapi.entity.OrderEntity;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

	List<OrderEntity> findByUserId(String userId);

	Optional<OrderEntity> findByRazorpayOrderId(String razorpayOrderId);

}
