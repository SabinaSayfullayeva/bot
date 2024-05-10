package com.example.project_for_doctors.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResDTO {
    private UUID id;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String skills;

    private String experience;

    private String location;

    private String phoneNumber;
}
