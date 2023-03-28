package com.example.sprintToDo.demo.controller.user;

import com.example.sprintToDo.demo.dto.CreateUserDto;
import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.dto.UserLogin;
import com.example.sprintToDo.demo.entity.User;
import com.example.sprintToDo.demo.service.user.UserService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto user) throws MailjetSocketTimeoutException, MailjetException {
        UserDto resultUser = userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping("")
    public  ResponseEntity<List<UserDto>> listUser(){
        List<UserDto> users = userService.listUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter")
    public  ResponseEntity<List<UserDto>> getUserFilter(@RequestBody UserDto user){
        List<UserDto> users = userService.getUserByFiltred(user);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody @Valid UserLogin user){
        Boolean result = userService.userLogin(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/excel")
    public ResponseEntity<Boolean> excelExportUser(@RequestBody UserLogin user){
        Boolean excelStatus = userService.excelExportUser(user);
        return ResponseEntity.ok(excelStatus);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto user, @PathVariable("id") Long id ){
        UserDto updateUser = userService.updateUser(user, id);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        Boolean deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }
}
