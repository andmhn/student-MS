package student_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.PaperDto;
import student_api.exeptions.DuplicatedInfoException;
import student_api.mapper.PaperMapper;
import student_api.repository.PaperRepository;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
public class PaperController {
    private final PaperRepository paperRepository;
    private final PaperMapper paperMapper;

    @PostMapping
    public PaperDto createPaper(@Valid @RequestBody PaperDto paperRequest) {
    	//check duplicated paper
    	if (paperRepository.findByName(paperRequest.getName()).isPresent()) {
    		throw new DuplicatedInfoException("Duplicated paper entry with name: " + paperRequest.getName());
    	}
    	
    	return paperMapper.toPaperDto(paperRepository.save(paperMapper.fromPaperDto(paperRequest)));
    }
}
