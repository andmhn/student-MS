package student_api.mapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.ResultRequest;
import student_api.controller.dto.ResultResponse;
import student_api.exeptions.NotFoundException;
import student_api.model.Result;
import student_api.repository.PaperRepository;

@Service
@RequiredArgsConstructor
public class ResultMapper {
	private final PaperRepository paperRepository;

	public Result fromResultRequest(ResultRequest resultRequest) {
		if (resultRequest == null) {
			return null;
		}

		Result result = new Result();

		result.setMarks(resultRequest.getMarks_obtained());
		result.setPaper(paperRepository.findByName(resultRequest.getPaper_id())
				.orElseThrow(() -> new NotFoundException("No Paper Entry for Paper: " + resultRequest.getPaper_id())));
		result.setStudent_email(resultRequest.getStudent_email());

		return result;
	}

	public ResultResponse toResultResponse(Result result) {
		if (result == null) {
			return null;
		}
		return new ResultResponse(result.getId(), result.getStudent_email(), result.getPaper().getName(), result.getMarks());
	}
}
