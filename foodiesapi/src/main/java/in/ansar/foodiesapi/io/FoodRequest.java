package in.ansar.foodiesapi.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class FoodRequest {
	
	public FoodRequest() {
		// TODO Auto-generated constructor stub
	}

	private String name;
	private String description;
	private double price;
	private String category;

}
