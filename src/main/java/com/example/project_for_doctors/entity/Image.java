package com.example.project_for_doctors.entity;

import com.example.project_for_doctors.entity.template.AbsUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.util.Date;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity(name = "images")
@Where(clause = "deleted=false")
public class Image extends AbsUUID {

    private String name;
    private String url;
    private String fileId;
    private Long size;

}
