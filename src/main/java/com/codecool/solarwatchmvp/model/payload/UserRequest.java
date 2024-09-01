package com.codecool.solarwatchmvp.model.payload;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}