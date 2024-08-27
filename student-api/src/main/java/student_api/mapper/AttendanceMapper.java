package student_api.mapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.AttendanceRequest;
import student_api.controller.dto.AttendanceResponse;
import student_api.model.Attendance;

@Service
@RequiredArgsConstructor
public class AttendanceMapper {
	public Attendance fromAttendanceRequest(AttendanceRequest attendanceRequest) {
		Attendance attendance = new Attendance();
		attendance.setId(0L);
		attendance.setDate(attendanceRequest.getDate());
		attendance.setStudent_email(null);
		attendance.setIs_present(attendanceRequest.getIs_present());
		attendance.setClass_id(attendanceRequest.getClass_id());
		attendance.setAbsent_reason(attendanceRequest.getAbsent_reason());
		
		return attendance;
	}

	public AttendanceResponse toAttendanceResponse(Attendance attendance) {
		return new AttendanceResponse(attendance.getId(), attendance.getStudent_email(), null,
				attendance.getClass_id(),attendance.getDate(), attendance.getIs_present(), attendance.getAbsent_reason());
	}
}
