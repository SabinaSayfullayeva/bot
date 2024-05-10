package com.example.project_for_doctors.service.imp;

import com.example.project_for_doctors.entity.User;
import com.example.project_for_doctors.manual.ApiResult;
import com.example.project_for_doctors.payload.UserResDTO;
import com.example.project_for_doctors.repository.UserRepository;
import com.example.project_for_doctors.service.abs.ProjectService;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.model.Parameter;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final UserRepository userRepository;

    @Override
    public ApiResult<UserResDTO> getUserByLastNameAndFirstName(String firstName, String lastName) {
        User user = userRepository.getUserByFistNameAndLastName(firstName, lastName);
        return ApiResult.success(toDTO(user));
    }

    public UserResDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserResDTO.UserResDTOBuilder userResDTO = UserResDTO.builder();
        userResDTO.id(user.getId());
        userResDTO.firstName(user.getFistName());
        userResDTO.lastName(user.getLastName());
        userResDTO.fatherName(user.getFatherName());
        userResDTO.skills(user.getSkills());
        userResDTO.experience(user.getExperience());
        userResDTO.location(user.getLocation());
        userResDTO.phoneNumber(user.getPhoneNumber());
        return userResDTO.build();
    }

}
