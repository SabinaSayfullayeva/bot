package com.example.project_for_doctors.repository;

import com.example.project_for_doctors.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
@Query(nativeQuery = true,value = "select * from images i join users u on i.id=u.images_id where u.chat_id=:chatId")
    Image findByUserChatId(String chatId);

}
