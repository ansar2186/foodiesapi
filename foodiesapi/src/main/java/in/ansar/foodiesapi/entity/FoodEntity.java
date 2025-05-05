package in.ansar.foodiesapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document(collection  = "food")
@Data
@AllArgsConstructor
@Builder
public class FoodEntity {
	
	 public FoodEntity() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	private String id;
	private String name;
	private String description;
	private double price;
	private String category;
	private String imageUrl;

}
