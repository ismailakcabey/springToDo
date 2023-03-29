package com.example.sprintToDo.demo.service.user;

import com.example.sprintToDo.demo.dto.CreateUserDto;
import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.dto.UserLogin;
import com.example.sprintToDo.demo.entity.User;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import java.util.List;

public interface UserService {
    UserDto createUser (CreateUserDto user) throws MailjetSocketTimeoutException, MailjetException;
    List<UserDto> listUser ();

    Boolean userLogin (UserLogin user);

    Boolean excelExportUser(String email);

    UserDto updateUser(UserDto user , Long id);

    Boolean deleteUser(Long id);

    UserDto getUserById(Long id);

    List<UserDto> getUserByFiltred(UserDto filter);
}
