package student_api.controller;

import static student_api.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.ResultRequest;
import student_api.controller.dto.ResultResponse;
import student_api.controller.dto.UserDto;
import student_api.mapper.ResultMapper;
import student_api.model.Result;
import student_api.model.User;
import student_api.repository.ResultRepository;
import student_api.security.CustomUserDetails;
import student_api.service.UserService;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {
	private final ResultRepository resultRepository;
	private final ResultMapper resultMapper;
	private final UserService userService;

	@PostMapping
	ResponseEntity<Result> publishResult(@Valid @RequestBody ResultRequest resultRequest) {
		// save to student
		Result savedResult = resultRepository.save(resultMapper.fromResultRequest(resultRequest));
		return ResponseEntity.ok(savedResult);
	}

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/me")
	public List<ResultResponse> getCurrentUserResults(@AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Result> results = resultRepository.findByEmail(currentUser.getEmail());

		List<ResultResponse> resultResponses = new ArrayList<>();

		results.forEach((result) -> {
			ResultResponse resultResponse = resultMapper.toResultResponse(result);
			resultResponses.add(resultResponse);
		});

		return resultResponses;
	}
}
