package student_api.mapper;

import org.springframework.stereotype.Service;

import student_api.controller.dto.UserDto;
import student_api.model.User;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }
}
