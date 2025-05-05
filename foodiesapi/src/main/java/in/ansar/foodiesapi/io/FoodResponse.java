package in.ansar.foodiesapi.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class FoodResponse {
	
	 public FoodResponse() {
		// TODO Auto-generated constructor stub
	}
	
	private String id;
	private String name;
	private String description;
	private String imageUrl;
	private double price;
	private String category;

}
