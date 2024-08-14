package student_api.controller;


import lombok.RequiredArgsConstructor;
import student_api.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    @GetMapping("/numberOfUsers")
    public Integer getNumberOfUsers() {
        return userService.getUsers().size();
    }
}
