package com.example.sprintToDo.demo.controller.user;

import com.example.sprintToDo.demo.dto.CreateUserDto;
import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.dto.UserLogin;
import com.example.sprintToDo.demo.entity.User;
import com.example.sprintToDo.demo.service.user.UserService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto user) throws MailjetSocketTimeoutException, MailjetException {
        UserDto resultUser = userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping("")
    public  ResponseEntity<List<UserDto>> listUser(){
        List<UserDto> users = userService.listUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody UserLogin user){
        Boolean result = userService.userLogin(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/excel")
    public ResponseEntity<Boolean> excelExportUser(@RequestBody UserLogin user){
        Boolean excelStatus = userService.excelExportUser(user);
        return ResponseEntity.ok(excelStatus);
    }
}
