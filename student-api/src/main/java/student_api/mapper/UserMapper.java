package student_api.mapper;

import student_api.controller.dto.UserDto;
import student_api.model.User;

public interface UserMapper {

    UserDto toUserDto(User user);
}