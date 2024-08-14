package student_api.mapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.PaperDto;
import student_api.model.Paper;

@Service
@RequiredArgsConstructor
public class PaperMapper {
	public Paper fromPaperDto(PaperDto paperDto) {
		if (paperDto == null) {
			return null;
		}
		Paper paper = new Paper();
		paper.setId(0L);
		paper.setName(paperDto.getName());
		paper.setDate(paperDto.getDate());
		paper.setType(paperDto.getType());
		return paper;
	}

	public PaperDto toPaperDto(Paper paper) {
		if (paper == null) {
			return null;
		}
		return new PaperDto(paper.getName(), paper.getDate(), paper.getType());
	}
}
