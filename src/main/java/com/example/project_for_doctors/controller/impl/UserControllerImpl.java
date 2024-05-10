package com.example.project_for_doctors.controller.impl;

import com.example.project_for_doctors.controller.UserController;
import com.example.project_for_doctors.manual.ApiResult;
import com.example.project_for_doctors.payload.UserResDTO;
import com.example.project_for_doctors.service.abs.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final ProjectService projectService;
    @Override
    public ApiResult<UserResDTO> getByFirstNameAndLastName(String first_name, String last_name) {
return projectService.getUserByLastNameAndFirstName(first_name,last_name);
    }
}
