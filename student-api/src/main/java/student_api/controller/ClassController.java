package student_api.controller;

import static student_api.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.AttendanceRequest;
import student_api.controller.dto.AttendanceResponse;
import student_api.controller.dto.ClassRequest;
import student_api.controller.dto.ClassResponse;
import student_api.exeptions.DuplicatedInfoException;
import student_api.exeptions.ForbiddenException;
import student_api.exeptions.NotFoundException;
import student_api.mapper.AttendanceMapper;
import student_api.mapper.ClassMapper;
import student_api.model.Attendance;
import student_api.model.Classes;
import student_api.repository.AttendanceRepository;
import student_api.repository.ClassesRepository;
import student_api.repository.UserRepository;
import student_api.security.CustomUserDetails;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {
	private final UserRepository userRepository;
	private final ClassesRepository classesRepository;
	private final ClassMapper classMapper;
	private final AttendanceRepository attendanceRepository;
	private final AttendanceMapper attendanceMapper;

	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@PostMapping
	public ResponseEntity<ClassResponse> createNewClass(@Valid @RequestBody ClassRequest classRequest) {
		Classes savedClass = classesRepository.save(classMapper.fromClassRequest(classRequest));

		return ResponseEntity.ok(classMapper.toClassResponse(savedClass));
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@DeleteMapping("/{class_id}")
	public void deleteClass(@PathVariable Long class_id) {
		classesRepository.deleteById(class_id);
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/{class_id}/hasUserEntryForToday")
	public boolean hasUserEntryForToday(
			@PathVariable Long class_id,
			@AuthenticationPrincipal CustomUserDetails currentUser
			) {
		LocalDate date = LocalDate.now();
		
		List<Attendance> attendances = attendanceRepository.findByEmail(currentUser.getEmail());
		for (Attendance a : attendances) {
			if( a.getClass_id().equals(class_id) &&
				a.getDate().equals(date)) {
				return true;
			}
		}
		return false;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@PostMapping("/attendances")
	public ResponseEntity<AttendanceResponse> createNewAttendance(
			@Valid @RequestBody AttendanceRequest attendanceRequest,
			@AuthenticationPrincipal CustomUserDetails currentUser
			) {
		String email = currentUser.getEmail();
		// check if class id exist
		if (!classesRepository.existsById(attendanceRequest.getClass_id())) {
			throw new NotFoundException("CLass Not found with id: " + attendanceRequest.getClass_id());
		}

		Attendance attendance = attendanceMapper.fromAttendanceRequest(attendanceRequest);
		attendance.setStudent_email(email);
		
		AttendanceResponse attendanceResponse = attendanceMapper
				.toAttendanceResponse(attendanceRepository.save(attendance));

		return ResponseEntity.ok(attendanceResponse);
	}
	

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/attendances/me")
	public List<AttendanceResponse> getCurrentUserAttendances(@AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Attendance> attendances = attendanceRepository.findByEmail(currentUser.getEmail());

		List<AttendanceResponse> attendanceResponse = new ArrayList<>();

		attendances.forEach((a) -> {
			attendanceResponse.add(attendanceMapper.toAttendanceResponse(a));
		});

		return attendanceResponse;
	}

	@Operation(security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@GetMapping("/attendances")
	public List<AttendanceResponse> getAllAttendance(@AuthenticationPrincipal CustomUserDetails currentUser) {
		String userRole = userRepository.findById(currentUser.getId()).get().getRole();
		if (!userRole.equals("ADMIN")) {
			throw new ForbiddenException("You are Forbidden from accessing all attendences with role: " + userRole);
		}

		List<Attendance> attendances = attendanceRepository.findAll();
		List<AttendanceResponse> attendanceResponse = new ArrayList<>();

		attendances.forEach((a) -> {
			attendanceResponse.add(attendanceMapper.toAttendanceResponse(a));
		});

		return attendanceResponse;
	}
}
