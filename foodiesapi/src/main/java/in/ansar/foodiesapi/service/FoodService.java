package in.ansar.foodiesapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.ansar.foodiesapi.io.FoodRequest;
import in.ansar.foodiesapi.io.FoodResponse;

public interface FoodService {

	String uploadFile(MultipartFile file);

	FoodResponse addFood(FoodRequest request, MultipartFile file);

	List<FoodResponse> getAllFoods();

	FoodResponse getFoodById(String id);

	boolean deleteFile(String fileName);
	
	void deleteFood(String id);

}
