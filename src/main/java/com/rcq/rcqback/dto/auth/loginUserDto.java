package com.rcq.rcqback.dto.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class loginUserDto implements Serializable {
    private String usermail;
    private String password;
}
