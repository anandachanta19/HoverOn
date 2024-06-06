package com.msp.hoveron.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msp.hoveron.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsersDto {
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("age")
    private Integer age;

}
