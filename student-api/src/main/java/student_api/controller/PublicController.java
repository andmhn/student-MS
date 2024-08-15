package student_api.controller;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.ClassResponse;
import student_api.mapper.ClassMapper;
import student_api.model.Classes;
import student_api.repository.ClassesRepository;
import student_api.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {
	private final ClassesRepository classesRepository;
	private final ClassMapper classMapper;
	private final UserService userService;

	@GetMapping("/numberOfUsers")
	public Integer getNumberOfUsers() {
		return userService.getUsers().size();
	}

	@GetMapping("/classes")
	public List<ClassResponse> allClasses() {
		List<Classes> classes = classesRepository.findAll();
		List<ClassResponse> classResponse = new ArrayList<>();

		for (Classes c : classes) {
			classResponse.add(classMapper.toClassResponse(c));
		}
		return classResponse;
	}

	@GetMapping("/classes/{class_id}")
	public ClassResponse getClassById(@PathVariable Long class_id) {
		Classes classById = classesRepository.findById(class_id).orElseThrow();
		return classMapper.toClassResponse(classById);
	}
}
