package com.example.project_for_doctors.service.abs;

import com.example.project_for_doctors.manual.ApiResult;
import com.example.project_for_doctors.payload.UserResDTO;

import java.util.UUID;

public interface ProjectService {
    ApiResult<UserResDTO> getUserByLastNameAndFirstName(String firts_name,String last_name);
}
