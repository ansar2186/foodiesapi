package in.ansar.foodiesapi.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import in.ansar.foodiesapi.io.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class OrderEntity {
	
	@Id
	private String id;
	private String userId;
	private String userAddress;
	private String phoneNumber;
	private String email;
	private double amount;
	private String paymentStatus;
	private String razorpayOrderId;
	private String razorpaySignature;
	private String razorpayPaymentId;
	private String orderStatus;
	private Date orderPlaceDate;
	private List<OrderItem> orderedItems;

}
