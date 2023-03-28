package com.example.sprintToDo.demo.repository;

import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
