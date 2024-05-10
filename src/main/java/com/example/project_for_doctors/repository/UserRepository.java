package com.example.project_for_doctors.repository;

import com.example.project_for_doctors.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByChatId(String chatId);

    User findByChatId(String chatId);

    User getUserByFistNameAndLastName(String firstName,String lastName);
}
