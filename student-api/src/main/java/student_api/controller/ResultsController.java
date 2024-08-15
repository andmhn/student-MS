package student_api.controller;

import static student_api.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.ResultRequest;
import student_api.controller.dto.ResultResponse;
import student_api.exeptions.DuplicatedInfoException;
import student_api.exeptions.ForbiddenException;
import student_api.exeptions.NotFoundException;
import student_api.mapper.ResultMapper;
import student_api.model.Result;
import student_api.repository.ResultRepository;
import student_api.security.CustomUserDetails;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {
	private final ResultRepository resultRepository;
	private final ResultMapper resultMapper;

	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@PostMapping
	ResponseEntity<Result> publishResult(@Valid @RequestBody ResultRequest resultRequest) {
		// save to student
		// A student should have only one result for a paper
		List<Result> resultsEntries = resultRepository.findByEmail(resultRequest.getStudent_email());

		for (Result result : resultsEntries) {

			if (result.getPaper().getName().equals(resultRequest.getPaper_id())) {
				throw new DuplicatedInfoException(
						"Duplicated entry for paper result with paper Id:" + resultRequest.getPaper_id());
			}
		}

		Result savedResult = resultRepository.save(resultMapper.fromResultRequest(resultRequest));
		return ResponseEntity.ok(savedResult);
	}
	
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping
	public List<ResultResponse> getAllResults() {
		List<Result> results = resultRepository.findAll();
		List<ResultResponse> resultResponses = new ArrayList<>();

		results.forEach((result) -> {
			ResultResponse resultResponse = resultMapper.toResultResponse(result);
			resultResponses.add(resultResponse);
		});

		return resultResponses;
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

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/{result_id}")
	public ResponseEntity<ResultResponse> getResultById(@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable Long result_id) {
		Result result = resultRepository.findById(result_id)
				.orElseThrow(() -> new NotFoundException("Result with id " + result_id + " Not Found!"));
		
		// check authorization
		if(result.getStudent_email().equals(currentUser.getEmail())) {
			return ResponseEntity.ok(resultMapper.toResultResponse(result));
		} else {
			throw new ForbiddenException("FORBIDDEN for User: " + currentUser.getEmail());
		}
	}
}
