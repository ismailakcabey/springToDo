package com.example.sprintToDo.demo.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    private Date createdAt;
    private Date updatedAt;
    private Long createdById;
    private Long updatedById;
}
