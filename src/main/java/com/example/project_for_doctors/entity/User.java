package com.example.project_for_doctors.entity;

import com.example.project_for_doctors.entity.template.AbsUUID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
public class User extends AbsUUID {
    @Column(nullable = false, name = "chat_id")
    private String chatId;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name ="father_name")
    private String fatherName;



    //Skillarni listini olmoqchi edim lkn vaqtim yetmaydi deb o'yladim ko'proq vaqt berilganda shunday qilardim

   /* @ManyToMany
    private List<Skills> skills;*/

    private String skills;



    @Column(name = "experience")
    private String experience;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;


//BOT dan imegeni olib DATABASE ga hozircha saqlolmadim
    /*@OneToOne(fetch = FetchType.LAZY)
    private Image image;*/


}
