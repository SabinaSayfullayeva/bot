package com.example.project_for_doctors.controller;

import com.example.project_for_doctors.entity.User;
import com.example.project_for_doctors.manual.ApiResult;
import com.example.project_for_doctors.payload.UserResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/example")
public interface UserController {

    String GET_BY_FIO="{first_name}"+"_"+"{last_name}";

    @GetMapping(GET_BY_FIO)
    ApiResult<UserResDTO> getByFirstNameAndLastName(@PathVariable String first_name,@PathVariable String last_name);
}
