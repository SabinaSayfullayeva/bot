package com.example.project_for_doctors.entity;

import com.example.project_for_doctors.entity.template.AbsUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity(name = "users")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
public class User extends AbsUUID {
    @Column(nullable = false, name = "chat_id")
    private String chatId;

    @Column(nullable = false ,name = "first_name")
    private String firstName;

    @Column(nullable = false ,name = "experience")
    private int experience;

    @Column(nullable = false ,name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false ,name = "location")
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    private Image image;


}
