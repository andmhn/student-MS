package student_api.controller;

import static student_api.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.PaperDto;
import student_api.exeptions.DuplicatedInfoException;
import student_api.exeptions.NotFoundException;
import student_api.mapper.PaperMapper;
import student_api.model.Paper;
import student_api.model.Result;
import student_api.repository.PaperRepository;
import student_api.repository.ResultRepository;
import student_api.security.CustomUserDetails;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
public class PaperController {
    private final PaperRepository paperRepository;
    private final ResultRepository resultRepository;
    private final PaperMapper paperMapper;

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
    @PostMapping
    public PaperDto createPaper(@Valid @RequestBody PaperDto paperRequest) {
    	//check duplicated paper
    	if (paperRepository.findByName(paperRequest.getName()).isPresent()) {
    		throw new DuplicatedInfoException("Duplicated paper entry with name: " + paperRequest.getName());
    	}
    	
    	return paperMapper.toPaperDto(paperRepository.save(paperMapper.fromPaperDto(paperRequest)));
    }
	
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
    @GetMapping
    public List<PaperDto> getAllPaper() {
		List<Paper> papers = paperRepository.findAll();
		List<PaperDto> paperResponses = new ArrayList<>();

		papers.forEach((p) -> {
			paperResponses.add(paperMapper.toPaperDto(p));
		});

		return paperResponses;
    }
	
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/{paper_name}")
	public ResponseEntity<PaperDto> getPaperByName(@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable String paper_name) {
		Paper paper = paperRepository.findByName(paper_name)
				.orElseThrow(() -> new NotFoundException("Paper with name " + paper_name + " Not Found!"));

		return ResponseEntity.ok(paperMapper.toPaperDto(paper));
	}

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/me")
	public List<PaperDto> getCurrentUserPapers(@AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Result> results = resultRepository.findByEmail(currentUser.getEmail());

		List<PaperDto> paperResponse = new ArrayList<>();

		results.forEach((r) -> {
			Paper p = paperRepository.findByName(r.getPaper().getName()).get();
			paperResponse.add(paperMapper.toPaperDto(p));
		});

		return paperResponse;
	}
}
