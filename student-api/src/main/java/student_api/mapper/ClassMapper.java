package student_api.mapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.ClassRequest;
import student_api.controller.dto.ClassResponse;
import student_api.model.Classes;

@Service
@RequiredArgsConstructor
public class ClassMapper {
	public Classes fromClassRequest(ClassRequest classRequest) {
		if (classRequest == null) {
			return null;
		}
		Classes class_table = new Classes();
		class_table.setId(0L);
		class_table.setName(classRequest.getName());
		class_table.setDescription(classRequest.getDescription());
		class_table.setType(classRequest.getType());
		class_table.setFromTime(classRequest.getFromTime());
		class_table.setToTime(classRequest.getToTime());

		return class_table;
	}

	public ClassResponse toClassResponse(Classes class_table) {
		if (class_table == null) {
			return null;
		}
		return new ClassResponse(class_table.getId(), class_table.getName(), class_table.getDescription(),
				class_table.getType(), class_table.getFromTime(), class_table.getToTime());
	}
}
