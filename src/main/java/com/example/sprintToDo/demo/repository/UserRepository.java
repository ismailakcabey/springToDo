package com.example.sprintToDo.demo.repository;

import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long>    {


    User findByEmail(String email);


    @Query("select u from User u where (:name IS NULL OR u.fullName=:name) AND (:mail IS NULL OR u.email = :mail)")
    List<User> findByFilter(@Param("name") String name, @Param("mail") String mail);
}
