package com.star.sud.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.star.sud.bean.User;

public class ConsumeRestApiClient {

	private static final String APP_URL = "http://localhost:8080/";

	private static void uploadSingleFile() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", getTestFile());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String serverUrl = APP_URL + "singlefileupload";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		System.out.println(response);
	}

	private static void uploadMultipleFile() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("files", getTestFile());
		body.add("files", getTestFile());
		body.add("files", getTestFile());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		String serverUrl = APP_URL + "multiplefileupload";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		System.out.println("Response code: " + response.getStatusCode());
	}

	private static void uploadSingleFileWithText() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		User user = new User(1, "sudarshan", "sudarshan@email.com");

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", getTestFile());
		body.add("user", user);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String serverUrl = APP_URL + "singlefileuploadWithText";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		System.out.println(response);
	}

	private static void uploadMultipleFileWithText() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		List<User> users = new ArrayList<>();
		users.add(new User(1, "sudarshan", "sudarshan@email.com"));
		users.add(new User(2, "john", "john@gmail.com"));
		users.addAll(users);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("files", getTestFile());
		body.add("files", getTestFile());
		body.add("users", users);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		String serverUrl = APP_URL + "multiplefileuploadWithText";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		System.out.println("Response code: " + response.getStatusCode());
	}

	public static Resource getTestFile() throws IOException {
		Path testFile = Files.createTempFile("test-file", ".txt");
		System.out.println("Creating and Uploading Test File: " + testFile);
		Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
		return new FileSystemResource(testFile.toFile());
	}

	public static void main(String[] args) {

		try {
			new ConsumeRestApiClient();
			// ConsumeRestApiClient.uploadSingleFile();
			// ConsumeRestApiClient.uploadMultipleFile();
			// ConsumeRestApiClient.uploadSingleFileWithText();
			ConsumeRestApiClient.uploadMultipleFileWithText();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
