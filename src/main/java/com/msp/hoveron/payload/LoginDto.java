package com.msp.hoveron.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;


}
