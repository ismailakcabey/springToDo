package com.example.sprintToDo.demo.dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private long id;
    @NotNull(message = "fullName is not null")
    private String fullName;
    @NotNull(message = "email is not null")
    private String email;
    @NotNull(message = "phoneNumber is not null")
    private String phoneNumber;
    @NotNull(message = "password is not null")
    private String password;
    @NotNull(message = "birthDateAt is not null")
    private Date birthDateAt;
}
