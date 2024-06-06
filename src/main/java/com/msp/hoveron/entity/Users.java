package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class Users {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "uname", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)

    private Gender gender;

    @Column(name = "age", nullable = false)
    private Integer age;


    public Users(Integer age, Gender gender, String email, String password, String userName, Long userId) {
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userId = userId;
    }


    public Users() {
    }

    @Override
    public String toString() {
        return "users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}

