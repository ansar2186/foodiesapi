package in.ansar.foodiesapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ansar.foodiesapi.io.FoodRequest;
import in.ansar.foodiesapi.io.FoodResponse;
import in.ansar.foodiesapi.service.FoodService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("*")
@AllArgsConstructor
public class FoodController {

	private final FoodService foodService;

	@PostMapping
	public FoodResponse addFood(@RequestPart("food") String foodString, @RequestPart("file") MultipartFile file) {

		ObjectMapper objectMapper = new ObjectMapper();
		FoodRequest request = null;
		try {
			request = objectMapper.readValue(foodString, FoodRequest.class);
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON formate");
		}
		return foodService.addFood(request, file);
	}

	@GetMapping
	public List<FoodResponse> getAllFoods() {
		return foodService.getAllFoods();
	}

	@GetMapping("/{id}")
	public FoodResponse getFoodById(@PathVariable("id") String id) {
		return foodService.getFoodById(id);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteFoodById(@PathVariable("id") String id) {
		foodService.deleteFood(id);

	}

}
