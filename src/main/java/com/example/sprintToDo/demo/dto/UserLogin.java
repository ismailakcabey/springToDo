package com.example.sprintToDo.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLogin {
    @NotNull(message = "password is not null")
    private String password;
    @NotNull(message = "email is not null")
    private String email;
}
