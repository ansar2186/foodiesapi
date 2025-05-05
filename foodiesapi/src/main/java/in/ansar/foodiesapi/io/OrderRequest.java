package in.ansar.foodiesapi.io;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

	private String userAddress;
	private double amount;
	private String email;
	private String phoneNumber;
	private String orderStatus;

	private List<OrderItem> orderedItems;

}
