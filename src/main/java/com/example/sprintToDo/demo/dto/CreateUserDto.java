package com.example.sprintToDo.demo.dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class CreateUserDto {
    @Id
    private long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private Date birthDateAt;
}
