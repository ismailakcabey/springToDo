package com.example.sprintToDo.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;

@Entity
@Table(name = "userTable")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
    @Id
    @SequenceGenerator(name="user_seq_gen",sequenceName = "user_gen",initialValue = 1,allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq_gen")
    private long id;
    @NotNull
    @Size(max = 100)
    private String fullName;
    @NotNull
    private String password;
    @Email
    @NotNull
    @Size(max = 100)
    @UniqueElements
    private String email;
    @Column()
    private Boolean emailStatus = false;
    @NotNull
    @Size(max = 20)
    private String phoneNumber;
    @NotNull
    private Date birthDateAt;
}
