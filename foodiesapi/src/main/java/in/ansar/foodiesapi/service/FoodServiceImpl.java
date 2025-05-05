package in.ansar.foodiesapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import in.ansar.foodiesapi.entity.FoodEntity;
import in.ansar.foodiesapi.io.FoodRequest;
import in.ansar.foodiesapi.io.FoodResponse;
import in.ansar.foodiesapi.repository.FoodRepository;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private S3Client s3Client;
	@Autowired
	private FoodRepository foodRepository;

	@Value("${aws.s3.bucketname}")
	private String bucketName;

	@Override
	public String uploadFile(MultipartFile file) {
		String fileNameExtention = file.getOriginalFilename()
				.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String key = UUID.randomUUID().toString() + "." + fileNameExtention;
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(key)
					.acl("public-read").contentType(file.getContentType()).build();

			PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

			if (response.sdkHttpResponse().isSuccessful()) {
				 return "https://"+bucketName+".s3.amazonaws.com/"+key;
			} else {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file upload fail");
			}

		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"An error occured while loading the file");
		}

	}

	@Override
	public FoodResponse addFood(FoodRequest request, MultipartFile file) {
		FoodEntity newFoodEntity = convertToEntity(request);
		String imageUrl = uploadFile(file);
		newFoodEntity.setImageUrl(imageUrl);
		newFoodEntity = foodRepository.save(newFoodEntity);

		return convertToResponse(newFoodEntity);
	}

	private FoodEntity convertToEntity(FoodRequest request) {
		return FoodEntity.builder().name(request.getName()).description(request.getDescription())
				.category(request.getCategory()).price(request.getPrice()).build();

	}

	private FoodResponse convertToResponse(FoodEntity foodEntity) {
		return FoodResponse.builder().id(foodEntity.getId()).name(foodEntity.getName())
				.description(foodEntity.getDescription()).imageUrl(foodEntity.getImageUrl())
				.price(foodEntity.getPrice()).category(foodEntity.getCategory()).build();

	}

	@Override
	public List<FoodResponse> getAllFoods() {
		List<FoodEntity> databaseEntries = foodRepository.findAll();
		return databaseEntries.stream().map(obj -> convertToResponse(obj)).collect(Collectors.toList());

	}

	@Override
	public FoodResponse getFoodById(String id) {
		FoodEntity dbFoodEntity = foodRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Food not found for the id : " + id));

		return convertToResponse(dbFoodEntity);

	}

	@Override
	public boolean deleteFile(String fileName) {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(fileName)
				.build();
		s3Client.deleteObject(deleteObjectRequest);
		return true;

	}

	@Override
	public void deleteFood(String id) {
		FoodResponse response = getFoodById(id);
		String imageUrl = response.getImageUrl();
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		boolean deleteFile = deleteFile(fileName);
		if (deleteFile) {
			foodRepository.deleteById(response.getId());
		}

	}

}
