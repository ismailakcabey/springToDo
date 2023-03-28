package com.example.sprintToDo.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private long id;
    private String fullName;
    private String email;
    private Boolean emailStatus;
    private String phoneNumber;
    private Date birthDateAt;
}
